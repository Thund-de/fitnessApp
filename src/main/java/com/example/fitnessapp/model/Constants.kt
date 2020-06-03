package com.example.fitnessapp.model

import com.example.fitnessapp.R

/**
 * Model der sich mit der Speicherung und Verwaltung von Daten beschäftigt.
 */
class Constants {
    // to make them accessable through the whole project, instead of creating instances
    // a companion object could be created, which means that it's the same thing as static in other classes
    companion object{
        /**
         * Eine Funktion, dass die Standarduebungen in eine Liste einpackt
         * Die Liste ist eine ArrayList.
         * Die Uebungsmodel enthaelt Informationen wie unsere Uebungen aussehen werden, wie sie heißen usw.
         */
        fun standardUebungsListe(): ArrayList<UebungsModel>{
            val uebungsListe = ArrayList<UebungsModel>()

            val hampelmann = UebungsModel(
                1,
                "Hampelmann",
                R.drawable.ex_one_hampelmann_pic,
                false,
                false
            )
            uebungsListe.add(hampelmann)

            val knieBeugen = UebungsModel(
                2,
                "Kniebeugen",
                R.drawable.ex_two_knie_beugen_pic,
                false,
                false
            )
            uebungsListe.add(knieBeugen)

            val beineUebung = UebungsModel(
                3,
                "Beine Übung",
                R.drawable.ex_three_beine_uebung_pic,
                false,
                false
            )
            uebungsListe.add(beineUebung)

            val brustmuskeltraining = UebungsModel(
                4,
                "Brustmuskeltraining Pushups",
                R.drawable.ex_four_brustmuskeltraining_pic,
                false,
                false
            )
            uebungsListe.add(brustmuskeltraining)

            val untereBrustmuskeltraining =
                UebungsModel(
                    5,
                    "Untere Brustmuskeltraining Pushups",
                    R.drawable.ex_five_untere_brustmuskeltraining_pic,
                    false,
                    false
                )
            uebungsListe.add(untereBrustmuskeltraining)

            val trizepsDips = UebungsModel(
                6,
                "Trizepdips",
                R.drawable.ex_six_trizeps_dips_pic,
                false,
                false
            )
            uebungsListe.add(trizepsDips)

            val trizepsstrecker = UebungsModel(
                7,
                "Trizepsstrecker",
                R.drawable.ex_seven_trizepsstrecker_pic,
                false,
                false
            )
            uebungsListe.add(trizepsstrecker)

            val waschbrettbauch = UebungsModel(
                8,
                "Waschbrettbauch",
                R.drawable.ex_eight_waschbrettbauch_pic,
                false,
                false
            )
            uebungsListe.add(waschbrettbauch)

            val unterarmstuetz = UebungsModel(
                9,
                "Unterarmstütz",
                R.drawable.ex_nine_unterarmstuetz_pic,
                false,
                false
            )
            uebungsListe.add(unterarmstuetz)

            return uebungsListe
        }
    }
}