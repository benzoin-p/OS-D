public class User {
    private String userName;
    UserCatalogue userCatalogue;
    User(String userName,UserCatalogue userCatalogue,MainCatalogue mainCatalogue)
    {
        this.userName = userName;
        this.userCatalogue = userCatalogue;
        mainCatalogue.userCatalogueMap.put(userName,userCatalogue);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserCatalogue getUserCatalogue() {
        return userCatalogue;
    }

    public void setUserCatalogue(UserCatalogue userCatalogue) {
        this.userCatalogue = userCatalogue;
    }
}
