package eu.netcoms.team.radeva.dr.myrecipe.validations;

public  class Validator {

    // Name
    public static final boolean nameHasInvalidLength(String word) {
        if(word.length() <= 2 || word.length() > 25 || word == null) {
            return true;
        }
        return false;
    }


    public static final boolean nameHasBadWords(String word) {
        if (word.toLowerCase().indexOf("fuck") != -1 || word.toLowerCase().indexOf("porn") != -1
                || word.toLowerCase().indexOf("bastard") != -1 || word.toLowerCase().indexOf("butt") != -1) {
            return true;
        }
        return false;
    }

    // Description
    public static final boolean descriptionHasInvalidLength(String word) {
        if(word.length() <= 2 || word.length() > 200 || word == null) {
            return true;
        }
        return false;
    }
}
