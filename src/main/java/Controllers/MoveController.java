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

    //todo
    //перейти к сессии
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

    /*
    @RequestMapping("/start1")
    public String startGame1(HttpServletRequest request, Model model) {
        return "redirect:/start";
    }
    @RequestMapping("/start2")
    public String startGame2(HttpServletRequest request, Model model) {
        return "redirect:http://tut.by";
    }
    */

    @RequestMapping("/move")
    public String makeMove(HttpServletRequest request, Model model) {
        int elementId = -1;
        try {
            elementId = Integer.valueOf(request.getParameter("id"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Random random = new Random();
        int randomNumber = -1;
        if (elementId < 0 || elementId >= (size * size) || crosses.get(elementId) >= 0 || possibleMoves == 0) {
            model.addAttribute("size", size);
            model.addAttribute("crosses", crosses);
            model.addAttribute("haveWinner", false);
            if (possibleMoves == 0)
                model.addAttribute("gameFinished", true);
            return "/index.jsp";
        }
        fillFieldPosition(model, "You", 1, elementId);
        if (possibleMoves != 0) {
            do {
                randomNumber = random.nextInt(size * size);
            } while (crosses.get(randomNumber) != -1);
            fillFieldPosition(model, "Computer", 0, randomNumber);
        } else
            model.addAttribute("gameFinished", true);
        model.addAttribute("size", size);
        model.addAttribute("crosses", crosses);
        return "/index.jsp";
    }

    private void fillFieldPosition(Model model, String player, int side, int movePosition) {
        crosses.set(movePosition, side);
        possibleMoves--;
        checkWinner(model, player);
    }

    private void checkWinner(Model model, String player) {
        if (checkField()) {
            model.addAttribute("haveWinner", true);
            model.addAttribute("winner", player);
            model.addAttribute("gameFinished", true);
            possibleMoves = 0;
        } else {
            model.addAttribute("haveWinner", false);
        }
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
