package com.example.mobmon.data

import android.util.Log
import android.widget.TextView
import com.example.mobmon.R
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class MSIParser {


    /*
        Companion Objects makes it so that you can call the function without initializing an object
        of it. The below function can be used like ``MSIParser.parseMSIData(data)``. It's Kotlin's
        equivalent of static methods.
     */
    companion object {

        /**
         * Header tags that should be ignored due to only being a container of similar content.
         */
        val MSI_HEADER_TAGS = listOf<String>("HardwareMonitor", "HardwareMonitorEntries", "HardwareMonitorGpuEntries")

        /**
         * Ignore list of tags not cared about, initialized with a debug header for the XML sheet.
         * Can be made into a feature for user filtering if interest exists.
         */
        val MSI_IGNORE_TAGS = mutableListOf<String>("HardwareMonitorHeader")

        /**
         * Entries on which one should create a new map for storing data, the first entry becomes the
         * indexing name in the main returning map.
         */
        val MSI_CREATE_TAGS = mutableListOf<String>("HardwareMonitorEntry", "HardwareMonitorGpuEntry")


        /**
         * Parses a MSI Afterburner Remote Server XML response.
         * @param data The returned XML sheet in string form.
         * @param encoding The character encoding of the XML sheet.
         * @return A map of all the available data points and their sub-values.
         */
        fun parseMSIData(data: String, encoding: String): MutableMap<String,MutableMap<String,String>> {

            val returnMap = mutableMapOf<String,MutableMap<String,String>>()

            val parser = XmlPullParserFactory.newInstance().newPullParser()
            val stream: InputStream = data.byteInputStream()
            parser.setInput(stream, encoding)
            var event = parser.eventType

            var currentMap = mutableMapOf<String,String>()
            var currentTag = "DEFAULT"
            var create = false

            while(event != XmlPullParser.END_DOCUMENT) {
                when (event) {
                    XmlPullParser.START_DOCUMENT -> Log.d("MSIParse", "Start of document parse.")
                    XmlPullParser.END_DOCUMENT -> Log.d("MSIParse", "End of document parse.")
                    XmlPullParser.START_TAG -> {

                        // Verbose logging, only for early debug.
                        //Log.v("MSIParse", "Found tag ${parser.name}")

                        when (parser.name) {
                            in MSI_HEADER_TAGS -> Log.v("MSIParse", "Skipping header tag [${parser.name}]")
                            in MSI_IGNORE_TAGS -> {
                                val curDepth = parser.depth
                                val skipName = parser.name

                                do {
                                    parser.next()
                                    Log.v("MSIParse", "Skipping tag [${parser.name}] due to it being below the tag [$skipName]")
                                } while (parser.depth != curDepth)
                            }
                            in MSI_CREATE_TAGS -> {
                                parser.next() // We have found a create tag, skip to the next one for retrieving the name.
                                currentMap = mutableMapOf<String,String>() // Init a fresh map.
                                currentTag = parser.name // Assign it to the returning map.
                                create = true
                            }
                            else -> currentTag = parser.name
                        }
                    }
                    XmlPullParser.TEXT -> {
                        if(parser.text != null) {
//                            Log.v("MSIParse", "Found text [${parser.text}]")

                            // If create is true, we are assigning a newly created map to the return structure.
                            if(create) {
                                returnMap[parser.text] = currentMap
                                create = false
                            }
                            else
                                currentMap[currentTag] = parser.text
                        }
                        else
                            Log.w("MSIParse", "Text property was null for ${parser.name} in a TEXT action")
                    }
                }

                event = parser.next()
            }
            Log.d("MSIParse", "Map contains ${returnMap.size} items, returning.")
            return returnMap
        }
    }


}