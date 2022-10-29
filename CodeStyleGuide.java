//  Class file naming style
//  Capitalize the first word in file name, use CamelCase, point what does this file do as short as posible, 
//  if the file name is too long, use understandable abbreviation.
//  ex. "MainMenu", "VarChangeListener"

//  Package naming style
//  Start with "ca.cmpt276.Calcium", you may add the directory name after that.
//  ex. "ca.cmpt276.Calcium.model", "ca.cmpt276.Calcium.UI"

/*  Paragraph Comment style
 *  Start on a new line, one tab after "//" or "/*" or "*". Same start position as the next line.
 */

//  Indentation style
//  Tab size is 4, use tab whenever you start a new line within the braces.

//  Class naming style
//  Capitalize the first word in file name, use CamelCase, describe what does this class do as short
//  as possible, if the file name is too long, use understandable abbreviation.
//  ex.
public class BattleStage;
private class HeroicSpirits {

    //  Constant naming style
    //  All letters in upper case, point what does this constant do as short as possible,
    //  if the file name is too long, use understandable abbreviation, connect the words by
    //  underlines '_'.
    //  ex.
    const String NAME = "Artoria Pendragon";
    final String CLASS_NAME = "Saber";

    //  Variable naming style
    //  All letters in lower case, point what does this constant do as short as possible,
    //  use CamelCase, if the file name is too long, use understandable abbreviation.
    //  ex.
    int currentHp;
    Card blueEyesWhiteDragon;

    //  Method/Function naming style
    //  Same as class naming style.
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


//  Resources file naming style
//  All letters in lower case, separate the words by '_', point what does this file do as short as posible, 
//  properly use abbreviation.   If it is a drawable file, add the type of it at the beginning.
//  ex. "main_menu_bg.png", "ic_map_port.png", "btn_main_menu_map.png", "battle_stage.xml"

//  ID naming style
//  All letters in lower case, start with the type of the element, connect the words with '_'.
//  ex. 
android:id="@+id/image_battle_field"
android:id="@+id/button_shuffle"
android:id="@+id/text_monster_effect"

//  String naming style in string.xml
//  All letters in lower case, start with the name of the file that this string is usd in, separate the 
//  words by '_', properly use abbreviation.
//  ex. 
<string name="activity_help_content"></string>
<string name="activity_menu_title"></string>
<string-array name="battle_stage_field_size">
    <item>5 x 5</item>
    <item>6 x 6</item>
</string-array>
