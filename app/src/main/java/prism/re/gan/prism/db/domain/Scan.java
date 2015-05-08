package prism.re.gan.prism.db.domain;

import java.util.Date;

/**
 * Created by rmbitiru on 4/12/15.
 */
public class Scan {

    private int id ;
    private Date scanDate ;
    private int scanNo ;
    private int beaconId ;

    public Scan () {}

    public Scan (int id, Date scanDate, int scanNo,
                 int beaconId) {
        setId(id);
        setScanDate(scanDate);
        setScanNo(scanNo);
        setBeaconId(beaconId);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    public int getScanNo() {
        return scanNo;
    }

    public void setScanNo(int scanNo) {
        this.scanNo = scanNo;
    }

    public int getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(int beaconId) {
        this.beaconId = beaconId;
    }
}
