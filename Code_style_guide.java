//  File name style
//  Capitalize the first word in file name, point what does this file do as short as posible, 
//  if the file name is too long, use understandable abbreviation, use CamelCase.
//  ex. "MainMenu.xml", "VarChangeListener.java", "ExcaliburBeam.png"

//  Package name style
//  Start with "ca.cmpt276.Calcium", you may add the directory name after that.
//  ex. "ca.cmpt276.Calcium.model", "ca.cmpt276.Calcium.UI"

/*  Paragraph Comment style
 *  Start on a new line, one tab after "//" or "/*" or "*". Same start position as the next line.
 */

//  Indentation style
//  Tab size is 4, use tab whenever you start a new line within the braces.

//  Class name style
//  Capitalize the first word in file name, use CamelCase, describe what does this class do as short
//  as possible, if the file name is too long, use understandable abbreviation.
//  ex.
public class BattleStage;
private class HeroicSpirits {

    //  Constant name style
    //  All letters in upper case, point what does this constant do as short as possible,
    //  if the file name is too long, use understandable abbreviation, connect the words by
    //  underlines '_'.
    //  ex.
    const String NAME = "Artoria Pendragon";
    final String CLASS_NAME = "Saber";

    //  Variable name style
    //  All letters in lower case, point what does this constant do as short as possible,
    //  use CamelCase, if the file name is too long, use understandable abbreviation.
    //  ex.
    int currentHp;
    Card blueEyesWhiteDragon;

    //  Method/Function name style
    //  Same as class name style.
    public void DestroyAllFaceUp(Card[][] field, int width, int height){

        //  Spacing style
        //  Space right after if, while, for etc.. Space on the both sides of an operator and after
        //  a comma or semicolon.
        //  ex.
        for (int i = 0; i < height; i += (height-1)){

            //  Brace style
            //  Opening brace at the end of the enclosing statement, closing brace on its own line,
            //  ex.
            for (int j = 0; j < width; j++){
                if (field[i][j].occupation == true
                    && field[i][j].face == 1){
                    field[i][j].destroy();        //  Insertion comment style
                }       //  2 tabs after the content before, and 1 tab after "//".
            }
        }
    }
}
