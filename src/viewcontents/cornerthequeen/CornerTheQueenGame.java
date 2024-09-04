package viewcontents.cornerthequeen;

import controller.AbstractController;
import sas.Circle;
import sas.Rectangle;
import sas.View;
import viewcontents.AbstractViewContent;

import java.awt.*;
import java.sql.SQLOutput;


public class CornerTheQueenGame extends AbstractViewContent {
    private static final String PATH_TO_IMAGES = PATH_TO_RESOURCES + "cornerthequeen/";

    private int groesseFelder;
    private Rectangle[][] fields ;
    private int[][] losingFields = new int[11][2];
    private Circle queen;
    private Rectangle zeroField;

    private Circle markerTurn;
    private boolean isTurn, turnWasStarted;
    boolean noTurnMoveActive;

    int tickBuffer;

    public CornerTheQueenGame(View view, AbstractController controller){
        super(view, controller);
    }

    @Override
    protected void initView() {
        tickBuffer = 10;
        isTurn = true;
        fields = new Rectangle[30][30];
        if(view.getHeight()<view.getWidth()){
            groesseFelder = (view.getHeight()/30);
        }else{
            groesseFelder = (view.getWidth()/30);
        }
        for(int i = 0;i<30;i=i+2){
            for(int j = 0;j<30;j=j+2){
                fields[j][i] = new Rectangle(j*groesseFelder,i*groesseFelder,groesseFelder,groesseFelder,Color.WHITE);
                fields[j+1][i] = new Rectangle((j+1)*groesseFelder,i*groesseFelder,groesseFelder,groesseFelder,Color.BLACK);
            }

            for(int j = 0;j<30;j=j+2){
                fields[j][i+1] = new Rectangle(j*groesseFelder,(i+1)*groesseFelder,groesseFelder,groesseFelder,Color.BLACK);
                fields[j+1][i+1] = new Rectangle((j+1)*groesseFelder,(i+1)*groesseFelder,groesseFelder,groesseFelder,Color.WHITE);
            }
        }
        zeroField =new Rectangle(0,29*groesseFelder,groesseFelder,groesseFelder,Color.green);
        queen = new Circle(29*groesseFelder,0, (double) groesseFelder /2,Color.pink);  //if middle aligned : ((view.getWidth()/2)+(14*groesseFelder))
        //System.out.println(queen.getCenterX() + " " + (queen.getShapeWidth()) + " " + (queen.getShapeWidth()/8));
        markerTurn = new Circle(queen.getCenterX()-(queen.getShapeWidth()/8),queen.getCenterY()-(queen.getShapeHeight()/8),queen.getShapeHeight()/4,Color.lightGray);//(3,3,4,PATH_TO_IMAGES + "meinbild.png");
        markerTurn.setHidden(true);
        //a & s as controls, enter for submit // joystick -> direction of joystick, (opposite direction or) button as submit
        //WIP

        //markerTurn = new Circle(queen.getCenterX()-(queen.getShapeWidth()/4),queen.getCenterY()-(queen.getShapeHeight()/4),queen.getShapeHeight()/4,Color.lightGray);//(3,3,4,PATH_TO_IMAGES + "meinbild.png");
        //        markerTurn.setHidden(true);
    }

    @Override
    public boolean tick() {
        if(view.keyPressed()){
            if(isTurn && tickBuffer == 0){
                //System.out.println("(tack)");
                if(!turnWasStarted){
                    tickBuffer = 10;
                    turn();
                    turnWasStarted = true;
                }else{
                    tickBuffer = 10;
                    //System.out.println("Hello");
                    turnMove();
                    //System.out.println("Hello World");
                }
            }
        }
        if(tickBuffer>0){
            tickBuffer--;
        }
        return !isFinish();
    }

    public void initLoosingFields(){
        boolean[] used = new boolean[30];
        for(int i = 0;i<30;i++){
            used[i] = false;
        }
        int tempNextValue = 1;
        for(int i = 0; i<(losingFields.length/2); i++){
            for(int j = 0;j<30;j++){
                if(!used[j]){
                    tempNextValue = j;
                }
            }
            losingFields[i][1] = tempNextValue;
            used[losingFields[i][1]] = false;
            losingFields[i][2] = losingFields[i][1]+i;
            used[losingFields[i][2]] = false;

        }
    }

    public boolean isInLosingPosition(int x, int y){
        for(int i = 0; i<(losingFields.length/2); i++){
            if(losingFields[i][1]==x || losingFields[i][2]==x){
                if(losingFields[i][1]==x && losingFields[i][2]==y){
                    return true;
                }
                if(losingFields[i][2]==x && losingFields[i][1]==y){
                    return true;
                }
            }
        }
        return false;
    }

    public void opponent(){
        if(isInLosingPosition((int) ((queen.getCenterX()-(queen.getShapeWidth()/2))/groesseFelder),(int) ((queen.getCenterY()-(queen.getShapeHeight()/2))/groesseFelder))){
            if(queen.getCenterX()-(queen.getShapeWidth()/2)-groesseFelder>=0) {
                moveToLeft();
            }else{
                moveDown();
            }
        }
        //WIP //move
        isTurn = true;
    }

    public void moveToLeft(){
        if(queen.getCenterX()-(queen.getShapeWidth()/2)-groesseFelder>=0) {
            queen.moveTo(queen.getCenterX() - (queen.getShapeWidth() / 2) - groesseFelder, queen.getCenterY() - (queen.getShapeHeight() / 2));
        }
    }

    public void moveDown(){
        if(queen.getCenterY()-(queen.getShapeHeight()/2)+groesseFelder<=((view.getHeight()/2)+(14*groesseFelder))){
            queen.moveTo(queen.getCenterX()-(queen.getShapeWidth()/2),queen.getCenterY()-(queen.getShapeHeight()/2)+groesseFelder);
        }

    }

    public void moveDiagonally(){
        if((queen.getCenterY()-(queen.getShapeHeight()/2)+groesseFelder<=((view.getHeight()/2)+(14*groesseFelder))) && (queen.getCenterX()-(queen.getShapeWidth()/2)-groesseFelder>=0)){
            queen.moveTo(queen.getCenterX()-(queen.getShapeWidth()/2)-groesseFelder,queen.getCenterY()-(queen.getShapeHeight()/2)+groesseFelder);
        }

    }

    public boolean isFinish(){
        return(zeroField.contains(queen));
    }

    public void turn(){
        markerTurn.moveTo(queen.getCenterX()-queen.getShapeWidth()/4,queen.getCenterY()-queen.getShapeHeight()/4);
        markerTurn.setHidden(false);
        noTurnMoveActive = true;
        turnMove();
        //wip //movement
        //view.keyPressed('a');
        //System.out.println("Yay?");
    }

    public void moveToLeftMarker(){
        if(markerTurn.getCenterX()-(markerTurn.getShapeWidth()/2)-groesseFelder>=0) {
            markerTurn.moveTo(markerTurn.getCenterX() - (markerTurn.getShapeWidth() / 2) - groesseFelder, markerTurn.getCenterY() - (markerTurn.getShapeHeight() / 2));
        }
    }

    public void moveDownMarker(){
        if(markerTurn.getCenterY()-(markerTurn.getShapeHeight()/2)+groesseFelder<=((view.getHeight()/2)+(14*groesseFelder)+markerTurn.getShapeHeight()/2)){
            markerTurn.moveTo(markerTurn.getCenterX()-(markerTurn.getShapeWidth()/2),markerTurn.getCenterY()-(markerTurn.getShapeHeight()/2)+groesseFelder);
        }

    }

    public void moveDiagonallyMarker(){
        if(((markerTurn.getCenterY()-(markerTurn.getShapeHeight()/2)+groesseFelder<=(((view.getHeight()/2)+(14*groesseFelder))+markerTurn.getShapeHeight()/2))) && ((markerTurn.getCenterX() - (markerTurn.getShapeWidth() / 2) - groesseFelder) >= 0)){
            markerTurn.moveTo(markerTurn.getCenterX()-(markerTurn.getShapeWidth()/2)-groesseFelder,markerTurn.getCenterY()-(markerTurn.getShapeHeight()/2)+groesseFelder);
        }

    }

    public void game(){ //turns, back&forth
        //WIP
    }

    public void turnMove(){
        if(!view.keyEnterPressed() && noTurnMoveActive){
            //System.out.println("EHHH?");
            noTurnMoveActive = false;
            turnMoveOnce();
            noTurnMoveActive = true;
        }else{
            isTurn = false;
            turnWasStarted = false;
            //WIP
        }
        //WIP
    }

    public void turnMoveOnce(){
        //System.out.println("Hello World!");
        if((view.keyPressed('a') || view.keyLeftPressed())){
            if((Math.round(markerTurn.getCenterY()))==Math.round((queen.getCenterY()))){
                if(markerTurn.getCenterX()>groesseFelder){
                    markerTurn.move(-groesseFelder,0);
                }
            }else if((Math.round(markerTurn.getCenterX()))==Math.round((queen.getCenterX()))){
                //whereIsInt(queen)[0]
                int[] markerLocation = whereIsInt(markerTurn);
                //int row = markerLocation[1];
                int[] queenLocation = whereIsInt(queen);
                if(markerLocation!=null && queenLocation!=null){
                    int differenceQueenXminusY = queenLocation[0]-queenLocation[1];
                    //int differenceYMarkerminusQueen = row-queenLocation[1];
                    System.out.println(markerLocation[1] + " " + differenceQueenXminusY + " " + groesseFelder);
                    //markerTurn.move(-(groesseFelder*(markerLocation[1]+differenceQueenXminusY)),0); //WIP
                    markerTurn.move(-((markerLocation[1]+differenceQueenXminusY)),0); //WIP //8*? // Move after to diagonal
                }
            }
        }
        if((view.keyPressed('s') || view.keyDownPressed())&& markerTurn.getCenterY()<29*groesseFelder){
            if((Math.round(markerTurn.getCenterX()))==Math.round((queen.getCenterX()))){
                markerTurn.move(0,groesseFelder);
            }
        }
        //WIP
    }
    public int[] whereIsInt(Circle c){
        for(int i = 0;i<30;i++){ //y
            for(int j = 0;j<30;j++){ //x
                if(fields[j][i].contains(c)){
                    int[] returnInt = new int[2];
                    returnInt[0] = j;
                    returnInt[1] = i;
                    return returnInt;
                }
            }
        }
        return null;
    }

}
