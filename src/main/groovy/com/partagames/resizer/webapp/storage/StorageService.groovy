package com.partagames.resizer.webapp.storage

import com.partagames.imageresizetool.ImageFormat
import org.springframework.core.io.Resource

import java.awt.image.BufferedImage
import java.nio.file.Path
import java.util.stream.Stream

interface StorageService {

    void init()

    void store(String fileName, ImageFormat imageFormat, BufferedImage scaledImage)

    Stream<Path> loadAll()

    Path load(String filename)

    Resource loadAsResource(String filename)

    void deleteAll()

}