public class SIng {
    private static SIng i;
    private String a = "";

    private SIng() {}


    static SIng init()
    {
        if (i == null)
        {
            i = new SIng();
        }

        return i;
    }

    void setA(String aa)
    {
        if (a.isEmpty()) {
            a = aa;
        }
    }

    public String getA() {
        return a;
    }
}
