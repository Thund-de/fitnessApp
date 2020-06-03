package com.example.fitnessapp.model

/**
 * Model der sich mit der Speicherung und Verwaltung von Daten beschäftigt.
 */
class UebungsModel(private var id: Int,
                    private var name: String,
                    private var bild: Int,
                    private var istErledigt: Boolean,
                    private var istAusgesucht: Boolean) {

    // Getters und Setters um auf sie jederzeit zugreifen zu koennen
    fun getId(): Int {
        return id
    }

    fun setId(id: Int){
        this.id = id
    }

    fun getName(): String{
        return name
    }

    fun setName(name: String){
        this.name = name
    }

    fun getBild(): Int{
        return bild
    }

    fun setBild(bild: Int){
        this.bild = bild
    }

    fun getIstErledigt(): Boolean{
        return istErledigt
    }

    fun setIstErledigt(istErledigt: Boolean){
        this.istErledigt = istErledigt
    }

    fun getIstAusgesucht(): Boolean{
        return istAusgesucht
    }

    fun setIstAusgesucht(istAusgesucht: Boolean){
        this.istAusgesucht = istAusgesucht
    }

}