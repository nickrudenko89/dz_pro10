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
        possibleMoves = size*size;
        crosses = new ArrayList<Integer>();
        for (int i = 0; i < size * size; i++)
            crosses.add(-1);
        model.addAttribute("size", size);
        model.addAttribute("crosses", crosses);
        return "/index.jsp";
    }

    @RequestMapping("/move")
    public String makeMove(HttpServletRequest request, Model model) {
        int elementId = Integer.valueOf(request.getParameter("id"));
        Random random = new Random();
        int randomNumber = -1;
        crosses.set(elementId, 1);
        possibleMoves--;
        if(possibleMoves!=0) {
            for (; ; ) {
                randomNumber = random.nextInt(size * size);
                if (crosses.get(randomNumber) == -1)
                    break;
            }
            crosses.set(randomNumber, 0);
            possibleMoves--;
        }
        model.addAttribute("size", size);
        model.addAttribute("crosses", crosses);
        return "/index.jsp";
    }
}
