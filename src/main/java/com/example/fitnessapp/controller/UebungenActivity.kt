package com.example.fitnessapp.controller

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.fitnessapp.R
import com.example.fitnessapp.model.Constants
import com.example.fitnessapp.model.UebungsModel
import kotlinx.android.synthetic.main.activity_uebungen.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Die Klasse behandelt die Uebungsaktivitiaet
 * Vererbt von APpCompatActivity und TextToSpeech und ruft die Schnittstelle OnItListener
 */
class UebungenActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    /**
     * Variablen für den Timer wöhrend der Ruhezeit
     */
    // Eine Variable fuer Zaehler, was im Nachhinein instaliziert wird
    private var countDownZeituhrZaehler: CountDownTimer? = null
    // es wird von 0 bis 4 hochzaehlen ..
    private var uebrigeFortschritt = 0
    private var restTimeDauer: Long = 4

    /**
     * Variablen für den Timer während der Übungszeit
      */
    //Timers fuer jede uebung
    private var uebungsTimer: CountDownTimer? = null
    private var uebungsFortschritt = 0
    private var uebungsTimerDauer: Long = 3

    // Neue Variable: fuer unsere UebungsListe
    private var uebungsListe: ArrayList<UebungsModel>? = null
    /*
    damit wir wissen in welcher Position wir uns finden.
    -1, da die Position einer Ubeung bei 0 startet. D.h. wenn ich inkremntiere fange ich sofort bei 0 an
     */
    private var aktuelleUebungsPosition = -1

    /**
     * Eine Variable, die wir fuer Vorlesen eines Textes verwenden werden
     */
    private var tts: TextToSpeech? = null

    /**
     * Eine Variable, die wir fuer Speichern eines Medias verweden werden
     */
    private var player: MediaPlayer? = null

    //var restTimerDauer

    // Das 2. Bildschirm, wo wir die unsere Ubeungen sehen werden
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uebungen)

        setSupportActionBar(id_toolbar_uebungs_aktivitaet)
        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true) // Actionbar aktivieren       ...// Wir wollen zur Hauptseit zurueckgehen, wenn wir aufm Zurueck Button klicken
        }
        // Mit das hier werden wir den Zurueckbutton haben
        id_toolbar_uebungs_aktivitaet.setNavigationOnClickListener {
            onBackPressed()
        }
        //this as the listener: it's the TextToSpeech which is implemented within our class
        tts = TextToSpeech(this, this   )

        // Wir wollen dass diese Funktion aufgerufen wird, wenn unserer Aktivitaet startet
        restViewEinrichten()

        uebungsListe = Constants.standardUebungsListe()
    }

    /**
     * Wir wollen sicherstellen, dass unsere Zeitzaehler zurueckgesetzt werden kann
     */
    override fun onDestroy() {
        if(countDownZeituhrZaehler != null){
            countDownZeituhrZaehler!!.cancel()
            uebrigeFortschritt = 0
        }
        if (uebungsTimer != null) {
            uebungsTimer!!.cancel()
            uebungsFortschritt = 0
        }

        // closes tesxtToSpeech if not used
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player!= null){
            player!!.stop()
        }
        
        super.onDestroy()
    }

    /**
     *  Funktion fuer unsere forschrittBar. setRestFortschrittsBar
     */
    private fun setRestFortschrittsBar() {
        // fortschrittBar hat eine Eigenschaft: progress. Wir setzen sie zum restFortschritt
        idFortschrittBar.progress = uebrigeFortschritt

        /*
         - Jede Sekunde wird es einmal getickt
         - restTimer.. It is a cound down timer. How? By using object colon then count on time and then
         - the brackets , we can now create this object.
         - uebungsTimer = object: CountDownTimer(uebungsTimerDauer *1000, 1000){
         */
        countDownZeituhrZaehler = object: CountDownTimer(restTimeDauer*1000, 1000){
            /*
             - Um die Funktionalität von CountDownTimer zu verwenden, wird es gebraucht, die onTick() Methode zu überschreiben
             -Es gibt 2 Methoden die man überschreiben kann:
               - 1. onTick: es wird bei jedem Tick ausgeführt
               - 2. onFinish: wenn die Zeit um ist, wird sie ausgeführts
             */
            override fun onTick(millisUntilFinished: Long) {
                //bei jedem einzelnen Tick , wird den übrigeFortschritt gesetzt und mit 1 inkrementiert
                uebrigeFortschritt++

                /*
                - in der gleichen Zeit wird das fortschrittBar.progress zu restTimeDauer minus die übrigeFortschritt gesetzt
                   restTimeDauer: kann immer von der Variable oben geändert werden. Ich werde es zu Testzwecke mit 4 initialisieren
                   übrigeFortschritt: wird immer nach jedem Tick inkrementiert und deswegen nach seinem Abzug bekommen wir die restZeit zum nächsten Fenster
                - Wenn den übrigeFortschritt bei 1 anfängt. Dann, den progress im FortschrittBar wird 3 sein (4-1) usw..
                 */
                idFortschrittBar.progress = restTimeDauer.toInt() - uebrigeFortschritt

                /*
                - Der Text was aufgezeigt wird:
                - 4 Sekunden minus den uebrigeFOrtschritt in String-Form.
                 */
                idZeitzaehler.text = (restTimeDauer.toInt()- uebrigeFortschritt).toString()
            }
            /**
             * Wenn die Stopuhr zu Ende kommt, dann wird den Text ausgegeben und ausgezeigt
             * Wenn die 4 Sekunden vorbei sind
              */
            override fun onFinish() {
                aktuelleUebungsPosition++
                uebungsViewEinrichten()

//                Toast.makeText(
//                    this@UebungenActivity,
//                    "Text zum Aufzeigen",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }.start() // der Zeitzaehler/Stoppuhr starten
    }

    /**
     *  Funktion fuer unsere uebungsforschrittBar bzw. Timer für jede Uebung.
     */
    private fun setUebungsFortschrittsBar() {
        // fortschrittBarUebung hat eine Eigenschaft: progress. Wir setzen sie zum uebungsFortschritt
        idFortschrittBarUebung.progress = uebungsFortschritt

        uebungsTimer = object: CountDownTimer(uebungsTimerDauer *1000, 1000){

            override fun onTick(millisUntilFinished: Long) {
                uebungsFortschritt++
                idFortschrittBarUebung.progress = uebungsTimerDauer.toInt() - uebungsFortschritt
                idUebungsZaehler.text = (uebungsTimerDauer.toInt() -uebungsFortschritt).toString()
            }

            override fun onFinish() {
                //Wenn alle Uebungen durch sind.. In unsrem Fall , wir haben nur 9 Uebungen
                if(aktuelleUebungsPosition < 8){
                    restViewEinrichten()
                }else {
                    // ich muss diese noch aendern
                    Toast.makeText(
                    this@UebungenActivity,
                    "Gratulation! Sie haben Ihre Training beendet",
                    Toast.LENGTH_SHORT
                ).show()
                }
            }
        }.start()
    }


    /**
     * Erzeugt ein audio bei dem MediaPlayer.creat aufgerufen wird und unser media-datei als argument eingeben
     * zeigt das Fenster zum Ausruhen auf
     * Zeigt den restFortschrittbar auf bzw unser Timer
     */
    private fun restViewEinrichten(){
        // Wir spielen unsere MediaPlayer Ton am Anfang (bei des Bereitmachens)
        try{
            // val soundURI = Uri.parse("android:resource://eu.")
            player = MediaPlayer.create(applicationContext,
                R.raw.get_ready
            )
            player!!.isLooping  = false
            player!!.start()
        } catch(e: Exception){
            e.printStackTrace()
        }

        idRestView.visibility = View.VISIBLE
        idUebungsView.visibility = View.GONE

        if(countDownZeituhrZaehler != null){
            countDownZeituhrZaehler!!.cancel() // Wenn sie nicht null, dann canceln
            uebrigeFortschritt = 0
        }

        setRestFortschrittsBar()
    }

    /**
     * Zeigt die Übungen und versteckt die restView Fenster während des Laufens jede Übung
     * Sie liest auch den Name jeder Übung vor
     *
     * Setzt und zeigt die Fotos zu jeder Übung auf
     */
    private fun uebungsViewEinrichten(){

        // um die TextLayouts sichtbar oder unsichtbar zu machen
        idRestView.visibility = View.GONE
        idUebungsView.visibility = View.VISIBLE

        if(uebungsTimer != null){
            uebungsTimer!!.cancel() // Wenn sie nicht null, dann canceln
            uebungsFortschritt = 0
        }

        /* Vorlesen
         - Es wird zu der Uebungsliste gehen und wird nachschauen, dass es eine ArraYliste ist.
         - Danach wird geschaut wo es herkommt > von UebungsModell. Dort haben wir z.B. einen Name.
         - Wenn wir nach dem Namen in Constants suchen, dann kann man den Uebungsname sehen.
         */
        lieseVor(uebungsListe!![aktuelleUebungsPosition].getName())

        setUebungsFortschrittsBar()

        /*
         - um das bild der Uebung zu bekommen
         - es gibt das bild von z.B. 01_hampelmann_pic zurueck.. getBild: UebungsModel -> image: R.drawable.01_hampelmann_pic(Constants)
         */
        idBild.setImageResource(uebungsListe!![aktuelleUebungsPosition].getBild())
        idUebungsName.text = uebungsListe!![aktuelleUebungsPosition].getName()

    }

    /**
     * Funktion fuer TextToSpeech
     */
    override fun onInit(status: Int){
        // Check if we can access textToSpeech
        if (status == TextToSpeech.SUCCESS){
            val resultat = tts!!.setLanguage(Locale.GERMAN)
            // Falls die Sprache nicht unterstuetzt ist, eine Nachricht aiusgeben
            if (resultat == TextToSpeech.LANG_MISSING_DATA || resultat == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "Die eingegebene Sprache ist nicht unterstützt")
            } else {
                Log.e("TTS", "Initialization fehlgeschlagen!")
            }
        }
    }

    /**
     * Eine Funktion, die den Text vorliest
     */
    @SuppressLint("NewApi")
    private fun lieseVor(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}
