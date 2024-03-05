package com.yi.truecaller.number.locator.name.finder.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("app")
    @Expose
    private App app;
    @SerializedName("customAd")
    @Expose
    private CustomAd customAd;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public CustomAd getCustomAd() {
        return customAd;
    }

    public void setCustomAd(CustomAd customAd) {
        this.customAd = customAd;
    }


    public class Ads {

        @SerializedName("google")
        @Expose
        private Google google;
        @SerializedName("google1")
        @Expose
        private Google1 google1;
        @SerializedName("facebook")
        @Expose
        private Facebook facebook;

        public Google getGoogle() {
            return google;
        }

        public void setGoogle(Google google) {
            this.google = google;
        }

        public Google1 getGoogle1() {
            return google1;
        }

        public void setGoogle1(Google1 google1) {
            this.google1 = google1;
        }

        public Facebook getFacebook() {
            return facebook;
        }

        public void setFacebook(Facebook facebook) {
            this.facebook = facebook;
        }

    }

    public class App {

        @SerializedName("appName")
        @Expose
        private String appName;
        @SerializedName("packageName")
        @Expose
        private String packageName;
        @SerializedName("terms")
        @Expose
        private String terms;
        @SerializedName("moreApp")
        @Expose
        private String moreApp;
        @SerializedName("privacyPolicyURL")
        @Expose
        private String privacyPolicyURL;
        @SerializedName("ads")
        @Expose
        private Ads ads;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }

        public String getMoreApp() {
            return moreApp;
        }

        public void setMoreApp(String moreApp) {
            this.moreApp = moreApp;
        }

        public String getPrivacyPolicyURL() {
            return privacyPolicyURL;
        }

        public void setPrivacyPolicyURL(String privacyPolicyURL) {
            this.privacyPolicyURL = privacyPolicyURL;
        }

        public Ads getAds() {
            return ads;
        }

        public void setAds(Ads ads) {
            this.ads = ads;
        }

    }

    public class CustomAd {

        @SerializedName("customBannerLink")
        @Expose
        private String customBannerLink;
        @SerializedName("customBannerImage")
        @Expose
        private String customBannerImage;
        @SerializedName("customInterstitialLink")
        @Expose
        private String customInterstitialLink;
        @SerializedName("customInterstitialImage")
        @Expose
        private String customInterstitialImage;
        @SerializedName("customNativeLink")
        @Expose
        private String customNativeLink;
        @SerializedName("customNativeImage")
        @Expose
        private String customNativeImage;
        @SerializedName("customSquareLink")
        @Expose
        private String customSquareLink;
        @SerializedName("customSquareImage")
        @Expose
        private String customSquareImage;
        @SerializedName("customRoundLink")
        @Expose
        private String customRoundLink;
        @SerializedName("customRoundImage")
        @Expose
        private String customRoundImage;
        @SerializedName("customShow")
        @Expose
        private Boolean customShow;

        public String getCustomBannerLink() {
            return customBannerLink;
        }

        public void setCustomBannerLink(String customBannerLink) {
            this.customBannerLink = customBannerLink;
        }

        public String getCustomBannerImage() {
            return customBannerImage;
        }

        public void setCustomBannerImage(String customBannerImage) {
            this.customBannerImage = customBannerImage;
        }

        public String getCustomInterstitialLink() {
            return customInterstitialLink;
        }

        public void setCustomInterstitialLink(String customInterstitialLink) {
            this.customInterstitialLink = customInterstitialLink;
        }

        public String getCustomInterstitialImage() {
            return customInterstitialImage;
        }

        public void setCustomInterstitialImage(String customInterstitialImage) {
            this.customInterstitialImage = customInterstitialImage;
        }

        public String getCustomNativeLink() {
            return customNativeLink;
        }

        public void setCustomNativeLink(String customNativeLink) {
            this.customNativeLink = customNativeLink;
        }

        public String getCustomNativeImage() {
            return customNativeImage;
        }

        public void setCustomNativeImage(String customNativeImage) {
            this.customNativeImage = customNativeImage;
        }

        public String getCustomSquareLink() {
            return customSquareLink;
        }

        public void setCustomSquareLink(String customSquareLink) {
            this.customSquareLink = customSquareLink;
        }

        public String getCustomSquareImage() {
            return customSquareImage;
        }

        public void setCustomSquareImage(String customSquareImage) {
            this.customSquareImage = customSquareImage;
        }

        public String getCustomRoundLink() {
            return customRoundLink;
        }

        public void setCustomRoundLink(String customRoundLink) {
            this.customRoundLink = customRoundLink;
        }

        public String getCustomRoundImage() {
            return customRoundImage;
        }

        public void setCustomRoundImage(String customRoundImage) {
            this.customRoundImage = customRoundImage;
        }

        public Boolean getCustomShow() {
            return customShow;
        }

        public void setCustomShow(Boolean customShow) {
            this.customShow = customShow;
        }

    }

    public class Facebook {

        @SerializedName("fbShow")
        @Expose
        private Boolean fbShow;
        @SerializedName("fbAppId")
        @Expose
        private String fbAppId;
        @SerializedName("fbBannerId")
        @Expose
        private String fbBannerId;
        @SerializedName("fbInterstitialId")
        @Expose
        private String fbInterstitialId;
        @SerializedName("fbNativeId")
        @Expose
        private String fbNativeId;
        @SerializedName("fbTimer")
        @Expose
        private Integer fbTimer;

        public Boolean getFbShow() {
            return fbShow;
        }

        public void setFbShow(Boolean fbShow) {
            this.fbShow = fbShow;
        }

        public String getFbAppId() {
            return fbAppId;
        }

        public void setFbAppId(String fbAppId) {
            this.fbAppId = fbAppId;
        }

        public String getFbBannerId() {
            return fbBannerId;
        }

        public void setFbBannerId(String fbBannerId) {
            this.fbBannerId = fbBannerId;
        }

        public String getFbInterstitialId() {
            return fbInterstitialId;
        }

        public void setFbInterstitialId(String fbInterstitialId) {
            this.fbInterstitialId = fbInterstitialId;
        }

        public String getFbNativeId() {
            return fbNativeId;
        }

        public void setFbNativeId(String fbNativeId) {
            this.fbNativeId = fbNativeId;
        }

        public Integer getFbTimer() {
            return fbTimer;
        }

        public void setFbTimer(Integer fbTimer) {
            this.fbTimer = fbTimer;
        }

    }

    public class Google {

        @SerializedName("googleShow")
        @Expose
        private Boolean googleShow;
        @SerializedName("adMobPublisherId")
        @Expose
        private String adMobPublisherId;
        @SerializedName("adMobAppId")
        @Expose
        private String adMobAppId;
        @SerializedName("adMobBannerId")
        @Expose
        private String adMobBannerId;
        @SerializedName("adMobInterstitialId")
        @Expose
        private String adMobInterstitialId;
        @SerializedName("adMobNativeId")
        @Expose
        private String adMobNativeId;
        @SerializedName("adMobRewardId")
        @Expose
        private String adMobRewardId;
        @SerializedName("googleTimer")
        @Expose
        private Integer googleTimer;

        public Boolean getGoogleShow() {
            return googleShow;
        }

        public void setGoogleShow(Boolean googleShow) {
            this.googleShow = googleShow;
        }

        public String getAdMobPublisherId() {
            return adMobPublisherId;
        }

        public void setAdMobPublisherId(String adMobPublisherId) {
            this.adMobPublisherId = adMobPublisherId;
        }

        public String getAdMobAppId() {
            return adMobAppId;
        }

        public void setAdMobAppId(String adMobAppId) {
            this.adMobAppId = adMobAppId;
        }

        public String getAdMobBannerId() {
            return adMobBannerId;
        }

        public void setAdMobBannerId(String adMobBannerId) {
            this.adMobBannerId = adMobBannerId;
        }

        public String getAdMobInterstitialId() {
            return adMobInterstitialId;
        }

        public void setAdMobInterstitialId(String adMobInterstitialId) {
            this.adMobInterstitialId = adMobInterstitialId;
        }

        public String getAdMobNativeId() {
            return adMobNativeId;
        }

        public void setAdMobNativeId(String adMobNativeId) {
            this.adMobNativeId = adMobNativeId;
        }

        public String getAdMobRewardId() {
            return adMobRewardId;
        }

        public void setAdMobRewardId(String adMobRewardId) {
            this.adMobRewardId = adMobRewardId;
        }

        public Integer getGoogleTimer() {
            return googleTimer;
        }

        public void setGoogleTimer(Integer googleTimer) {
            this.googleTimer = googleTimer;
        }

    }

    public class Google1 {

        @SerializedName("google1Show")
        @Expose
        private Boolean google1Show;
        @SerializedName("adMob1PublisherId")
        @Expose
        private String adMob1PublisherId;
        @SerializedName("adMob1AppId")
        @Expose
        private String adMob1AppId;
        @SerializedName("adMob1BannerId")
        @Expose
        private String adMob1BannerId;
        @SerializedName("adMob1InterstitialId")
        @Expose
        private String adMob1InterstitialId;
        @SerializedName("adMob1NativeId")
        @Expose
        private String adMob1NativeId;
        @SerializedName("adMob1RewardId")
        @Expose
        private String adMob1RewardId;
        @SerializedName("google1Timer")
        @Expose
        private Integer google1Timer;

        public Boolean getGoogle1Show() {
            return google1Show;
        }

        public void setGoogle1Show(Boolean google1Show) {
            this.google1Show = google1Show;
        }

        public String getAdMob1PublisherId() {
            return adMob1PublisherId;
        }

        public void setAdMob1PublisherId(String adMob1PublisherId) {
            this.adMob1PublisherId = adMob1PublisherId;
        }

        public String getAdMob1AppId() {
            return adMob1AppId;
        }

        public void setAdMob1AppId(String adMob1AppId) {
            this.adMob1AppId = adMob1AppId;
        }

        public String getAdMob1BannerId() {
            return adMob1BannerId;
        }

        public void setAdMob1BannerId(String adMob1BannerId) {
            this.adMob1BannerId = adMob1BannerId;
        }

        public String getAdMob1InterstitialId() {
            return adMob1InterstitialId;
        }

        public void setAdMob1InterstitialId(String adMob1InterstitialId) {
            this.adMob1InterstitialId = adMob1InterstitialId;
        }

        public String getAdMob1NativeId() {
            return adMob1NativeId;
        }

        public void setAdMob1NativeId(String adMob1NativeId) {
            this.adMob1NativeId = adMob1NativeId;
        }

        public String getAdMob1RewardId() {
            return adMob1RewardId;
        }

        public void setAdMob1RewardId(String adMob1RewardId) {
            this.adMob1RewardId = adMob1RewardId;
        }

        public Integer getGoogle1Timer() {
            return google1Timer;
        }

        public void setGoogle1Timer(Integer google1Timer) {
            this.google1Timer = google1Timer;
        }

    }
}
