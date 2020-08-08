package git.hyeonsoft.rhythm.game;

import java.util.ArrayList;

public class Bpm
{
    public Bpm(float bpm, int 마디, int 박, int 분)
    {
        this.bpm = bpm;
        this.마디 = 마디;
        this.박 = 박;
        this.분 = 분;
        time = 0;
    }
    public float getMdi()
    {
        return 마디 + (float)박 / 분;
    }
    public float bpm;
    public float time;
    public int 마디;
    public int 박;
    public int 분;
}
class Bpms
{
    public ArrayList<Bpm> bpms;
    public void calculateTime() {
        for (int i = 1; i < bpms.size(); i++) {
            bpms.get(i).time = bpms.get(i-1).time+ (bpms.get(i).마디 + (float)bpms.get(i).박 / bpms.get(i).분-bpms.get(i-1).마디 - (float)bpms.get(i-1).박 / bpms.get(i-1).분) * (60 / Math.abs(bpms.get(i-1).bpm));
        }
    }
}