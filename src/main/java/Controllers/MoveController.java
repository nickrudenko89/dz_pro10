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
        //проверка по строкам
        boolean isWinner = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < (size - 1); j++) {
                if (field[i][j] >= 0) {
                    if (field[i][j] != field[i][j + 1]) {
                        isWinner = false;
                        break;
                    } else
                        isWinner = true;
                } else {
                    isWinner = false;
                    break;
                }
            }
            if (isWinner)
                return true;
        }
        //проверка по столбцам
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < (size - 1); i++) {
                if (field[i][j] >= 0) {
                    if (field[i][j] != field[i + 1][j]) {
                        isWinner = false;
                        break;
                    } else
                        isWinner = true;
                } else {
                    isWinner = false;
                    break;
                }
            }
            if (isWinner)
                return true;
        }
        //1ая диагональ
        for (int i = 0; i < (size - 1); i++) {
            if (field[i][i] >= 0) {
                if (field[i][i] != field[i + 1][i + 1]) {
                    isWinner = false;
                    break;
                } else {
                    isWinner = true;
                }
            } else {
                isWinner = false;
                break;
            }
        }
        if (isWinner)
            return true;
        //2ая диагональ
        for (int i = (size - 1); i > 0; i--) {
            if (field[i][size - 1 - i] >= 0) {
                if (field[i][size - 1 - i] != field[i - 1][size - i]) {
                    isWinner = false;
                    break;
                } else {
                    isWinner = true;
                }
            } else {
                isWinner = false;
                break;
            }
        }
        if (isWinner)
            return true;
        return false;
    }
}
