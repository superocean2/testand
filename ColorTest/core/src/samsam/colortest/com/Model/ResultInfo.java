package samsam.colortest.com.Model;

/**
 * Created by NghiaTrinh on 7/9/2015.
 */
public class ResultInfo {
    private String _picture;
    private String _picture_fb;
    private String _name;
    private String _description1;
    private String _description2;

    public ResultInfo(String _picture, String _name, String _description1, String _description2,String _picture_fb) {
        this._picture = _picture;
        this._name = _name;
        this._description1 = _description1;
        this._description2 = _description2;
        this._picture_fb=_picture_fb;
    }

    public String get_picture() {
        return _picture;
    }

    public void set_picture(String _picture) {
        this._picture = _picture;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_description1() {
        return _description1;
    }

    public void set_description1(String _description1) {
        this._description1 = _description1;
    }

    public String get_description2() {
        return _description2;
    }

    public void set_description2(String _description2) {
        this._description2 = _description2;
    }

    public String get_picture_fb() {
        return _picture_fb;
    }

    public void set_picture_fb(String _picture_fb) {
        this._picture_fb = _picture_fb;
    }
}
