package nl.bos.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "Envelope")
public class SoapProcessorsEnvelope {
    @JacksonXmlProperty(localName = "Header")
    private SoapHeader soapHeader;
    @JacksonXmlProperty(localName = "Body")
    private Body body;

    @JsonIgnoreType
    private static class SoapHeader {
    }

    private static class Body {
        @JacksonXmlProperty(localName = "GetSoapProcessorsResponse")
        private SoapProcessorsResponse soapProcessorsResponse;

        private static class SoapProcessorsResponse {
            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "tuple")
            private List<Tuple> tuples;

            private static class Tuple {
                @JacksonXmlProperty(localName = "old")
                private Old old;

                private static class Old {
                    @JacksonXmlProperty(localName = "entry")
                    private Entry entry;

                    @JsonIgnoreProperties
                    private static class Entry {
                        @JsonProperty
                        private String dn;
                        @JsonProperty
                        private String entryUUID;
                        @JacksonXmlProperty(localName = "bussoapprocessorconfiguration")
                        private List<String> busSoapProcessorConfiguration;
                        @JacksonXmlProperty(localName = "computer")
                        private List<String> computer;
                        @JacksonXmlProperty(localName = "description")
                        private List<String> description;
                        @JacksonXmlProperty(localName = "busosprocesshost")
                        private List<String> busOsProcessHost;
                        @JacksonXmlProperty(localName = "automaticstart")
                        private List<String> automaticStart;
                        @JacksonXmlProperty(localName = "cn")
                        private List<String> cn;
                        @JacksonXmlProperty(localName = "objectclass")
                        private List<String> objectClass;
                    }
                }
            }
        }
    }
}
