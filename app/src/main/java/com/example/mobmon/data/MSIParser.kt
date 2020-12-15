package com.example.mobmon.data

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class MSIParser {

    companion object {
        // Header tags that should be ignored due to only being a container of similar content
        private val MSI_HEADER_TAGS = listOf<String>("HardwareMonitor", "HardwareMonitorEntries", "HardwareMonitorGpuEntries")

        // Ignore list of tags not cared about, initialized with a debug header for the XML sheet
        private val MSI_IGNORE_TAGS = mutableListOf<String>("HardwareMonitorHeader")

        // Entries on which one should create a new map for storing data
        private val MSI_CREATE_TAGS = mutableListOf<String>("HardwareMonitorEntry", "HardwareMonitorGpuEntry")

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

            while (event != XmlPullParser.END_DOCUMENT) {
                when (event) {
                    XmlPullParser.START_TAG -> {
                        when (parser.name) {
                            in MSI_IGNORE_TAGS -> {
                                val curDepth = parser.depth
                                val skipName = parser.name
                                do {
                                    parser.next()
                                } while (parser.depth != curDepth)
                            }
                            in MSI_CREATE_TAGS -> {
                                parser.next()
                                currentMap = mutableMapOf<String,String>()
                                currentTag = parser.name
                                create = true
                            }
                            else -> currentTag = parser.name
                        }
                    }
                    XmlPullParser.TEXT -> {
                        if (parser.text != null) {
                            if (create) {
                                returnMap[parser.text] = currentMap
                                create = false
                            }
                            else
                                currentMap[currentTag] = parser.text
                        }
                    }
                }
                event = parser.next()
            }
            return returnMap
        }
    }
}
