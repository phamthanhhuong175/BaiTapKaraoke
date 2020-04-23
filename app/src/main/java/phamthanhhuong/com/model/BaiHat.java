package phamthanhhuong.com.model;

public class BaiHat {
    private String Ma;
    private String Ten;
    private String casi;
    private int thich;

    public BaiHat(String ma, String ten, String casi, int thich) {
        Ma = ma;
        Ten = ten;
        this.casi = casi;
        this.thich = thich;
    }

    public BaiHat() {
    }

    public void setMa(String ma) {
        Ma = ma;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public void setCasi(String casi) {
        this.casi = casi;
    }

    public void setThich(int thich) {
        this.thich = thich;
    }

    public String getMa() {
        return Ma;
    }

    public String getTen() {
        return Ten;
    }

    public String getCasi() {
        return casi;
    }

    public int getThich() {
        return thich;
    }
}
