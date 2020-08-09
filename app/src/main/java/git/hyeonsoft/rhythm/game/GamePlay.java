package git.hyeonsoft.rhythm.game;

import android.opengl.GLES30;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import git.hyeonsoft.rhythm.MainGLRenderer;
import git.hyeonsoft.rhythm.object3d.Cube;
import git.hyeonsoft.rhythm.object3d.GameObject;
import git.hyeonsoft.rhythm.object3d.GameObject3d;
import git.hyeonsoft.rhythm.object3d.Square;

public class GamePlay {
    private GameObject lane;
    private GameObject3d lane_end;

    public static float LANE_LENGTH = 200f;
    public static float LANE_SIZE = 4f;

    public int combo=0;
    private float nowDist=0;
    private int currentBpmIndex = 0;
    public static float leftEnd;
    public static float rightEnd;
    public static float middle;
    boolean timeStart = false;
    double startTime = 0d;
    double currentTime = 0d;
    double playTime = 0d;
    float 배속=0.25f;
    int 마디 = 1;
    ArrayList<Note> notes;
    ArrayList<NoteBar> noteBars;
    Bpms bpmList;
    public GameObject comboText;
    public GameObject noteGameObject;
    public GameObject noteBarGameObject;
    private MainGLRenderer renderer;

    public GamePlay(MainGLRenderer renderer){
        GLES30.glClearColor(0.5f, 0.4f, 0.3f, 1);
        //바닥
        lane = new Square(new float[]{
                -LANE_SIZE/2,0, -0f,
                LANE_SIZE/2, 0, 0,
                LANE_SIZE/2, 0, LANE_LENGTH,
                -LANE_SIZE/2, 0, LANE_LENGTH
        },
                new float[] {0.01f, 0.01f, 0.01f, 1});
        lane_end = new Cube(new float[]{0, 0.05f, 0}, new float[]{LANE_SIZE, 0.1f, 0.1f}, new float[]{0.7f, 0.7f, 0.7f, 1f});

        this.renderer = renderer;


        bpmList = new Bpms();
        bpmList.bpms = new ArrayList<>();
        bpmList.bpms.add(new Bpm(60, 0, 0, 4));
        notes = new ArrayList<>();
        noteBars = new ArrayList<>();
        bpmList.calculateTime();
        AddNote(-0.3f, 0.3f, 2, 0, 4);
        AddNote(0, 0.3f, 2, 1, 8);
        AddNote(0.3f, 0.4f, 2, 2, 8);
        AddNote(-0.3f, 0.3f, 3, 0, 4);
        AddNote(0, 0.3f, 3, 1, 4);
        AddNote(0.4f, 0.2f, 3, 2, 4);
        AddNote(-0.1f, 0.2f, 3, 2, 4);
        AddNote(-0.1f, 0.2f, 4, 1, 4);
        AddNote(-0.1f, 0.2f, 4, 2, 4);
        AddNote(-0.1f, 0.2f, 4, 3, 4);
    }

    float GetNoteDistance(int 마디, int 박, int 분)
    {
        //거리 = 배속*시그마((마디 + 박 / 분))
        float mdi = 마디 + (float)박 / 분;
        return mdi*배속*30;
    }
    float GetNoteTime(int 마디, int 박, int 분)
    {
        //시간 = 시그마(마디+박/분)*(60/bpm)
        float time=0;
        float prev = 0;
        float mdi = 마디 + (float)박 / 분;
        for (int i = 0; i < bpmList.bpms.size(); i++)
        {
            try
            {
                if (mdi > bpmList.bpms.get(i + 1).getMdi())
                {
                    time = bpmList.bpms.get(i + 1).time;
                    prev = bpmList.bpms.get(i + 1).getMdi();
                }
                else
                {
                    time += (mdi - prev) * 60 / bpmList.bpms.get(i).bpm;
                    break;
                }
            }
            catch (Exception e)
            {
                //maybe OutOfIndexException
                time += (mdi - prev) * 60 / Math.abs(bpmList.bpms.get(i).bpm);
                break;
            }
        }
        return time;
    }
    boolean AddNote(float x, float size, int madi, int bak, int bun)
    {
        float dist = GetNoteDistance(madi, bak, bun)-nowDist;
        if (dist > LANE_LENGTH) return false;
        notes.add(new Note(GetNoteTime(madi, bak, bun), size*LANE_SIZE, x, dist));
        return true;
    }
    public void draw(float[] mMVPMatrix){
        //시간 처리
        if(!timeStart){
            startTime = (double)System.nanoTime()/1000000000;
            currentTime = startTime;
            timeStart=true;
        }
        float deltaTime = (float)((double)System.nanoTime()/1000000000 - currentTime);
        currentTime = (double)System.nanoTime()/1000000000;
        playTime += deltaTime;
        //Log.i(deltaTime+", "+playTime, "draw: "+nowDist);
        //노트 속도 처리
        Note.NoteSpeed = bpmList.bpms.get(currentBpmIndex).bpm/2*배속;
        //거리 처리
        nowDist += Note.NoteSpeed * deltaTime;
        //bpm 변속 처리
        while (currentBpmIndex+1 < bpmList.bpms.size()) {
            if (bpmList.bpms.get(currentBpmIndex + 1).time < playTime)
            {
                currentBpmIndex++;
            }
            else
            {
                break;
            }
        }
        //노트 마디 처리
        if (AddNoteBar(마디))
        {
            마디++;
            //Log.i("마디", ""+마디);
        }
        for (int i= 0;i < noteBars.size();i++)
        {
            if (noteBars.get(i).isTimeOver(playTime))
            {
                noteBars.remove(i);
            }else{
                break;
            }
        }
        //노트 처리
        for (int i = 0; i < notes.size(); i++)
        {
            if (notes.get(i).isTimeOver(playTime))
            {
                gotNote(i);
            }else{
                break;
            }
        }
        //draw
        Iterator it = noteBars.iterator();
        while(it.hasNext()){
            NoteBar x = (NoteBar)it.next();
            x.update(deltaTime);
            x.draw(mMVPMatrix);
        }
        it = ((ArrayList<Note>)(notes.clone())).iterator();
        while(it.hasNext()){
            try {
                Note x = (Note) it.next();
                x.update(deltaTime);
                x.draw(mMVPMatrix);
            }catch(NullPointerException e){

            }
        }
        lane_end.draw(mMVPMatrix);
        lane.draw(mMVPMatrix);
        //터치 범위 설정
        float[] touchPan=MainGLRenderer.vec3ToScreenPos(mMVPMatrix, -LANE_SIZE/2, 0, 0);
        rightEnd = touchPan[0];
        leftEnd = -rightEnd;
        middle = touchPan[1];
    }

    boolean AddNoteBar(int madi) {
        float dist = GetNoteDistance(madi, 0, 4) - nowDist;
        if (dist > LANE_LENGTH) return false;
        //Log.i("notebar", "AddNoteBar: "+madi+", "+GetNoteDistance(madi, 0, 4)+", "+GetNoteTime(madi, 0, 4)+", "+nowDist);
        noteBars.add(new NoteBar(GetNoteTime(madi, 0, 4), LANE_SIZE, dist));
        return true;
    }

    private void gotNote(int idx){
        float delTime = (float)Math.abs(notes.get(idx).time - playTime);
        Log.i("idx"+notes.get(idx).notePosX, ", "+delTime);
        notes.remove(idx);
        //Score, combo
        if (delTime > 0.3) {
            //bad
            combo = 0;
        }else if(delTime>0.05){
            //near
            combo++;
        }else{
            //perfect
            combo++;
        }
        Log.i("combo", ", "+combo);
    }

    private void noteTouchHandle(float pos){
        /*Log.i("touchpos", "n"+pos);*/
        for(int i=0;i<notes.size();i++){
            if(playTime - notes.get(i).time < -0.1)break;
            else if(notes.get(i).notePosX-notes.get(i).noteSize/2<pos&&
            pos<notes.get(i).notePosX+notes.get(i).noteSize/2){
                //Log.i("touchpos"+i, ", "+pos);
                gotNote(i);
            }
        }
    }

    public void touch(float x, float y){
        //Log.i(middle+", "+ leftEnd, ", "+leftEnd+", "+x);
        if(middle - 0.5<y&&y<middle+0.5&&leftEnd<x&&x<rightEnd){
            noteTouchHandle(x/(rightEnd-leftEnd)*2);
        }
    }
}
