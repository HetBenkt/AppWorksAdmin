package nl.bos.awp;

public enum AppWorksPlatformImpl implements AppWorksPlatform {
    INSTANCE;

    public boolean ping() {
        String healthCheckURL = "http://192.168.56.110:8080/home/system/app/mp/health/ready";
        AppWorksPlatformService awpService = new AppWorksPlatformServiceImpl(healthCheckURL);
        return awpService.ping();
    }
}
