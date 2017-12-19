package Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Random;

@Controller
@RequestMapping("/do")
public class MoveController {

    private ArrayList<Integer> crosses;
    private int size;
    private int possibleMoves;

    @RequestMapping("/start")
    public String startGame(HttpServletRequest request, Model model) {
        size = Integer.valueOf(request.getParameter("size"));
        possibleMoves = size * size;
        crosses = new ArrayList<Integer>();
        for (int i = 0; i < size * size; i++)
            crosses.add(-1);
        model.addAttribute("size", size);
        model.addAttribute("crosses", crosses);
        model.addAttribute("haveWinner", false);
        return "/index.jsp";
    }

    @RequestMapping("/move")
    public String makeMove(HttpServletRequest request, Model model) {
        int elementId = Integer.valueOf(request.getParameter("id"));
        Random random = new Random();
        int randomNumber = -1;
        crosses.set(elementId, 1);
        possibleMoves--;
        model = checkWinner(model, "You");
        if (possibleMoves != 0) {
            for (; ; ) {
                randomNumber = random.nextInt(size * size);
                if (crosses.get(randomNumber) == -1)
                    break;
            }
            crosses.set(randomNumber, 0);
            possibleMoves--;
            model = checkWinner(model, "Computer");
        }
        model.addAttribute("size", size);
        model.addAttribute("crosses", crosses);
        return "/index.jsp";
    }

    private Model checkWinner(Model model, String player) {
        if (checkField()) {
            model.addAttribute("haveWinner", true);
            model.addAttribute("winner", player);
            possibleMoves = 0;
        } else {
            model.addAttribute("haveWinner", false);
        }
        return model;
    }

    private boolean checkField() {
        int[][] field = new int[size][size];
        int elementCounter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = crosses.get(elementCounter);
                elementCounter++;
            }
        }
        //проверка победы по строкам и по диагоналям 1ч
        for (int i = 0; i < size; i++) {
            for (int j = 0; (j + 2) < size; j++) {
                if (field[i][j] >= 0) {
                    //по строке
                    if ((field[i][j] == field[i][j + 1]) && (field[i][j] == field[i][j + 2]))
                        return true;
                    //по 1-ой части диагоналей
                    if ((i + 2) < size)
                        if ((field[i][j] == field[i + 1][j + 1]) && (field[i][j] == field[i + 2][j + 2]))
                            return true;
                }
            }
        }
        //проверка победы по столбцам и по диагоналям 2ч
        for (int i = (size - 1); (i - 2) >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] >= 0) {
                    //по столбцу
                    if ((field[i][j] == field[i - 1][j]) && (field[i][j] == field[i - 2][j]))
                        return true;
                    //по 2-ой части диагоналей
                    if ((j + 2) < size)
                        if ((field[i][j] == field[i - 1][j + 1]) && (field[i][j] == field[i - 2][j + 2]))
                            return true;
                }
            }
        }
        return false;
    }
}
