package nl.bos;

public class SoapWebServiceContext {
    private SoapWebServiceStrategy soapWebServiceStrategy;

    public SoapWebServiceContext(SoapWebServiceStrategy soapWebServiceStrategy){
        this.soapWebServiceStrategy = soapWebServiceStrategy;
    }
    public String execute() {
        return soapWebServiceStrategy.run();
    }
}
