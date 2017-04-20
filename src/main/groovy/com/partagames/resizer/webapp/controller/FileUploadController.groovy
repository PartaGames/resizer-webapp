package com.partagames.resizer.webapp.controller

import com.partagames.imageresizetool.Dimensions
import com.partagames.imageresizetool.ImageFormat
import com.partagames.imageresizetool.ImageScaleTool
import com.partagames.imageresizetool.ScalingHint
import com.partagames.resizer.webapp.storage.StorageFileNotFoundException
import com.partagames.resizer.webapp.storage.StorageService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import org.springframework.web.servlet.mvc.support.RedirectAttributes

import javax.imageio.ImageIO
import javax.servlet.http.HttpServletRequest
import java.awt.image.BufferedImage
import java.util.stream.Collectors

@Slf4j
@Controller
class FileUploadController {

    private final StorageService storageService
    private final ImageScaleTool imageScaleTool

    @Autowired
    FileUploadController(StorageService storageService, ImageScaleTool imageScaleTool) {
        this.storageService = storageService
        this.imageScaleTool = imageScaleTool
    }

    @GetMapping("/")
    String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService
                .loadAll()
                .map({ path ->
            MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                    .build().toString()
        })
                .collect(Collectors.toList()))

        return "uploadForm"
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename)
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file)
    }

    @PostMapping("/")
    String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        final Map<String, BufferedImage> imageFiles = new HashMap<>()
        imageFiles.put(file.getOriginalFilename(), ImageIO.read(new ByteArrayInputStream(file.getBytes())))
        final Dimensions[] dimensions = new Dimensions[1]

        def width = Integer.parseInt(request.getParameter("width"))
        def height = Integer.parseInt(request.getParameter("height"))

        dimensions[0] = new Dimensions(width, height)
        def format = ImageFormat.getByValue(request.getParameter("outputFormat"))
        def hint = ScalingHint.getByValue(request.getParameter("scalingHint"))

        def resultImages = imageScaleTool.scale(imageFiles, dimensions, format, hint)
        def fileName = resultImages.keySet()[0]

        storageService.store(fileName, format, resultImages.get(fileName))
        redirectAttributes.addFlashAttribute("message",
                "You successfully scaled " + file.originalFilename + " to " + fileName + "!")

        return "redirect:/"
    }

    File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename())
        multipart.transferTo(convFile)
        return convFile
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
