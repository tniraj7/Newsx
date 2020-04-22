package com.example.newsx.model

class Article() {

     var author: String? = null
     var content: String? = null
     var description: String? = null
     var publishedAt: String? = null
     var source: Source? = null
     var title: String? = null
     var url: String? = null
     var urlToImage: String? = null

     constructor(author: String, content: String ,description: String, publishedAt: String, source: Source, title: String, url: String, urlToImage: String): this() {

         this.author = author
         this.content = content
         this.description = description
         this.publishedAt = publishedAt
         this.source = source
         this.title = title
         this.url = url
         this.urlToImage = urlToImage
     }

 }

 class Source() {

     var id: String? = null
     var name: String? = null

     constructor(id: String, name: String): this() {
         this.id = id
         this.name = name
     }

 }

