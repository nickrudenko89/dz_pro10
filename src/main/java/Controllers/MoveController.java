package Controllers;

import Classes.Tictactoe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Random;

@Controller
@RequestMapping("/do")
public class MoveController {

    @RequestMapping("/start")
    public String startGame(@RequestParam(name = "size", required = false, defaultValue = "3") int size, HttpSession session, Model model) {
        Tictactoe tictactoe = new Tictactoe(size);
        session.setAttribute("tictactoe", tictactoe);
        model.addAttribute("haveWinner", false);
        return "/index.jsp";
    }

    @RequestMapping("/move")
    public String makeMove(HttpServletRequest request, HttpSession session, Model model) {
        int elementId = -1;
        try {
            elementId = Integer.valueOf(request.getParameter("id"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Tictactoe tictactoe = (Tictactoe) session.getAttribute("tictactoe");
        int size = tictactoe.getSize();
        Random random = new Random();
        int randomNumber;
        if (elementId < 0 || elementId >= (size * size) || tictactoe.getCrosses().get(elementId) >= 0 || tictactoe.getPossibleMoves() == 0) {
            model.addAttribute("haveWinner", false);
            if (tictactoe.getPossibleMoves() == 0)
                model.addAttribute("gameFinished", true);
            return "/index.jsp";
        }
        fillFieldPosition(tictactoe, model, "You", 1, elementId);
        if (tictactoe.getPossibleMoves() != 0) {
            do {
                randomNumber = random.nextInt(size * size);
            } while (tictactoe.getCrosses().get(randomNumber) != -1);
            fillFieldPosition(tictactoe, model, "Computer", 0, randomNumber);
        } else
            model.addAttribute("gameFinished", true);
        return "/index.jsp";
    }

    private void fillFieldPosition(Tictactoe tictactoe, Model model, String player, int side, int movePosition) {
        tictactoe.setCrosses(side, movePosition);
        tictactoe.setPossibleMoves(tictactoe.getPossibleMoves() - 1);
        checkWinner(tictactoe, model, player);
    }

    private void checkWinner(Tictactoe tictactoe, Model model, String player) {
        if (checkField(tictactoe)) {
            model.addAttribute("haveWinner", true);
            model.addAttribute("winner", player);
            model.addAttribute("gameFinished", true);
            tictactoe.setPossibleMoves(0);
        } else {
            model.addAttribute("haveWinner", false);
        }
    }

    private boolean checkField(Tictactoe tictactoe) {
        int size = tictactoe.getSize();
        ArrayList<Integer> crosses = tictactoe.getCrosses();
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
        return isWinner;
    }
}
