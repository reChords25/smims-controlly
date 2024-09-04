package viewcontents.cornerthequeen;

import controller.AbstractController;
import sas.Circle;
import sas.Rectangle;
import sas.View;
import viewcontents.AbstractViewContent;

import java.awt.*;

public class CornerTheQueenGame extends AbstractViewContent {
    private static final String PATH_TO_IMAGES = PATH_TO_RESOURCES + "cornerthequeen/";

    private int groesseFelder;
    private Rectangle[][] fields ;
    private int[][] losingFields = new int[11][2];
    private Circle queen;
    private Rectangle zeroField;
    private Circle markerTurn;

    public CornerTheQueenGame(View view, AbstractController controller){
        super(view, controller);
    }

    @Override
    protected void initView() {
        fields = new Rectangle[30][30];
        if(view.getHeight()<view.getWidth()){
            groesseFelder = (view.getHeight()/30);
        }else{
            groesseFelder = (view.getWidth()/30);
        }
        for(int i = 0;i<30;i=i+2){
            for(int j = 0;j<30;j=j+2){
                fields[j][i] = new Rectangle(j*groesseFelder,i*groesseFelder,groesseFelder,groesseFelder);
                fields[j][i].setColor(Color.blue);
                fields[j+1][i] = new Rectangle(j*groesseFelder,i*groesseFelder,groesseFelder,groesseFelder);
                fields[j+1][i].setColor(Color.BLACK);
            }

            for(int j = 0;j<15;j++){
                fields[j][i+1] = new Rectangle(j*groesseFelder,i*groesseFelder,groesseFelder,groesseFelder);
                fields[j][i+1].setColor(Color.BLACK);
                fields[j+1][i+1] = new Rectangle(j*groesseFelder,i*groesseFelder,groesseFelder,groesseFelder);
                fields[j+1][i+1].setColor(Color.orange);
            }
        }
        Rectangle zeroField =new Rectangle(0,view.getHeight()-groesseFelder,groesseFelder,groesseFelder);
        zeroField.setColor(Color.green);
        queen = new Circle(((view.getWidth()/2)+(14*groesseFelder)),0,groesseFelder/2,Color.blue);
        markerTurn = new Circle(queen.getCenterX()+(queen.getShapeWidth()/4),queen.getCenterY()+(queen.getShapeHeight()/4),queen.getShapeHeight()/4,Color.lightGray);//(3,3,4,PATH_TO_IMAGES + "meinbild.png");
        markerTurn.setHidden(true);
        //a & s as controls, enter for submit // joystick -> direction of joystick, (opposite direction or) button as submit
        //WIP
    }

    @Override
    public boolean tick() {
        return false;
    }

    public void initScreen(){
        /**markerTurn = new Circle(queen.getCenterX()+(queen.getShapeWidth()/4),queen.getCenterY()+(queen.getShapeHeight()/4),queen.getShapeHeight()/4,Color.lightGray);//(3,3,4,PATH_TO_IMAGES + "meinbild.png");
        markerTurn.setHidden(true);
        if(view.getHeight()<view.getWidth()){
            groesseFelder = (view.getHeight()/30);
        }else{
            groesseFelder = (view.getWidth()/30);
        }
        for(int i = 0;i<30;i=i+2){
            for(int j = 0;j<30;j=j+2){
                fields[j][i] = new Rectangle(j*groesseFelder,i*groesseFelder,groesseFelder,groesseFelder);
                fields[j][i].setColor(Color.WHITE);
                fields[j+1][i] = new Rectangle(j*groesseFelder,i*groesseFelder,groesseFelder,groesseFelder);
                fields[j+1][i].setColor(Color.BLACK);
            }

            for(int j = 0;j<15;j++){
                fields[j][i+1] = new Rectangle(j*groesseFelder,i*groesseFelder,groesseFelder,groesseFelder);
                fields[j][i+1].setColor(Color.BLACK);
                fields[j+1][i+1] = new Rectangle(j*groesseFelder,i*groesseFelder,groesseFelder,groesseFelder);
                fields[j+1][i+1].setColor(Color.WHITE);
            }
        }
        Rectangle zeroField =new Rectangle(0,view.getHeight()-groesseFelder,groesseFelder,groesseFelder);
        zeroField.setColor(Color.green);
        queen = new Circle(((view.getWidth()/2)+(14*groesseFelder)),0,groesseFelder/2,Color.blue);
        //a & s as controls, enter for submit // joystick -> direction of joystick, (opposite direction or) button as submit
        //WIP**/
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

        //WIP
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

}
