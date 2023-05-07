package com.tolgasaglam.mezunapp

class Kullanici {
    var kulad:String = ""
    var kulsoyad:String = ""
    var kulgirisyil:Int = 0
    var kulmezunyil:Int = 0
    var kulsifre:String = ""
    constructor(kulad:String,kulsoyad:String,kulgirisyil:Int,kulmezunyil:Int,kulsifre:String){
        this.kulad = kulad
        this.kulsoyad = kulsoyad
        this.kulgirisyil = kulgirisyil
        this.kulmezunyil = kulmezunyil
        this.kulsifre = kulsifre
    }
}