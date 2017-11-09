package org.dormitory.autobotsoccub.utils;

import lombok.SneakyThrows;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.engine.model.GameData;
import org.dormitory.autobotsoccub.engine.model.MatchTeam;
import org.dormitory.autobotsoccub.engine.scores.ScoreTable;
import org.dormitory.autobotsoccub.engine.scores.ScoreTableRecord;
import org.telegram.telegrambots.api.objects.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

// This is a temporary solution
public class ImageGenerator {

    public static File updateImage(GameData gameData, ChatData chatData) {

        List<String> userRows = getUserRows(gameData, chatData);
        Map<MatchTeam, Integer> gameScore = gameData.getScoreTable().getGameScore();

        String text = format(PATTERN_FOR_GAME_END,
                gameScore.get(MatchTeam.A),
                gameScore.get(MatchTeam.B),
                userRows.get(0), userRows.get(1), userRows.get(0), userRows.get(1));
        return generateImage(text,550, 300);
    }

    public static File updateImage(String event, ChatData chatData) {

        GameData gameData = chatData.getGameEngineOperations().getCurrentGameData(chatData.getUser().getId());
        List<String> userRows = getUserRows(gameData, chatData);
        Map<MatchTeam, Integer> gameScore = gameData.getScoreTable().getGameScore();

        String score = format(SCORE, gameScore.get(MatchTeam.A), gameScore.get(MatchTeam.B));
        String text = format(PATTERN_FOR_GAME_PROCESSING,
                event, score,
                userRows.get(0), userRows.get(1), userRows.get(0), userRows.get(1));
        return generateImage(text,500,340);
    }

    private static List<String> getUserRows(GameData gameData, ChatData chatData) {
        List<String> userRows = new ArrayList<>();

        ScoreTable scoreTable;
        Map<Integer, ScoreTableRecord> scoreTableRecordMap;
        ScoreTableRecord scoreTableRecord;

        for (Map.Entry<Integer, User> pair : chatData.getGameProcessUserPool().entrySet()) {
            scoreTable = gameData.getScoreTable();
            scoreTableRecordMap = scoreTable.getStatsByUserId();
            scoreTableRecord = scoreTableRecordMap.get(pair.getKey());
            userRows.add(format(USER_ROW, pair.getValue().getFirstName(),
                    scoreTableRecord.getPosition(),
                    scoreTableRecord.getScored().get(),
                    scoreTableRecord.getAutoScored().get(),
                    scoreTableRecord.getMissed().get()));
        }
        return userRows;
    }

    @SneakyThrows
    private static File generateImage(String text, int width, int height) {

        JLabel label = new JLabel(text);
        label.setSize(width, height);

        BufferedImage image = new BufferedImage(
                label.getWidth(), label.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        {
            Graphics g = image.getGraphics();
            label.paint(g);
            g.dispose();
        }

        File outputFile = new File("test.png");
        ImageIO.write(image, "png", outputFile);
        return outputFile;
    }

    private static final String SCORE= "Score: %d-%d";

    private static final String USER_ROW =
                    "   <td class=\"tg-baqh\">%s</td>" +
                    "   <td class=\"tg-baqh\">%s</td>" +
                    "   <td class=\"tg-baqh\">%d</td>" +
                    "   <td class=\"tg-baqh\">%d</td>" +
                    "   <td class=\"tg-baqh\">%d</td>";

    private static final String PATTERN_FOR_GAME_PROCESSING = "<html>" +
            "<style type=\"text/css\">\n" +
            ".tg  {border-collapse:collapse;border-spacing:0;border-color:#bbb;border:none;}\n" +
            ".tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#bbb;color:#594F4F;background-color:#E0FFEB;}\n" +
            ".tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#bbb;color:#493F3F;background-color:#9DE0AD;}\n" +
            ".tg .tg-baqh{text-align:center;vertical-align:top}\n" +
            ".tg .tg-0l6a{background-color:#C2FFD6;text-align:center;vertical-align:top}\n" +
            "</style>\n" +
            "<table class=\"tg\" style=\"undefined;table-layout: fixed; width: 386px\">\n" +
            "<colgroup>\n" +
            "<col style=\"width: 151px\">\n" +
            "<col style=\"width: 67px\">\n" +
            "<col style=\"width: 43px\">\n" +
            "<col style=\"width: 70px\">\n" +
            "<col style=\"width: 55px\">\n" +
            "</colgroup>\n" +
            "  <tr>\n" +
            "    <th class=\"tg-baqh\" colspan=\"5\">%s</th>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td class=\"tg-0l6a\" colspan=\"5\">%s</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td class=\"tg-baqh\">Name</td>\n" +
            "    <td class=\"tg-baqh\">Position</td>\n" +
            "    <td class=\"tg-baqh\">Goal</td>\n" +
            "    <td class=\"tg-baqh\">AutoGoal</td>\n" +
            "    <td class=\"tg-baqh\">Missed</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "       %s" +
            "  </tr>\n" +
            "  <tr>\n" +
            "       %s" +
            "  </tr>\n" +
            "  <tr>\n" +
            "       %s" +
            "  </tr>\n" +
            "  <tr>\n" +
            "       %s" +
            "  </tr>\n" +
            "</table>" +
            "</html>";


    private static final String PATTERN_FOR_GAME_END = "<html>" +
            "<style type=\"text/css\">\n" +
            ".tg  {border-collapse:collapse;border-spacing:0;border-color:#bbb;border:none;}\n" +
            ".tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#bbb;color:#594F4F;background-color:#E0FFEB;}\n" +
            ".tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:0px;overflow:hidden;word-break:normal;border-color:#bbb;color:#493F3F;background-color:#9DE0AD;}\n" +
            ".tg .tg-baqh{text-align:center;vertical-align:top}\n" +
            ".tg .tg-0l6a{background-color:#C2FFD6;text-align:center;vertical-align:top}\n" +
            "</style>\n" +
            "<table class=\"tg\" style=\"undefined;table-layout: fixed; width: 427px\">\n" +
            "<colgroup>\n" +
            "<col style=\"width: 59px\">\n" +
            "<col style=\"width: 143px\">\n" +
            "<col style=\"width: 64px\">\n" +
            "<col style=\"width: 41px\">\n" +
            "<col style=\"width: 67px\">\n" +
            "<col style=\"width: 53px\">\n" +
            "</colgroup>\n" +
            "  <tr>\n" +
            "    <th class=\"tg-baqh\" colspan=\"6\">Game end with score %d-%d</th>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td class=\"tg-0l6a\">Status</td>\n" +
            "    <td class=\"tg-0l6a\">Name</td>\n" +
            "    <td class=\"tg-0l6a\">Position</td>\n" +
            "    <td class=\"tg-0l6a\">Goal</td>\n" +
            "    <td class=\"tg-0l6a\">AutoGoal</td>\n" +
            "    <td class=\"tg-0l6a\">Missed</td>\n" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td class=\"tg-baqh\" rowspan=\"2\"><br>Winner</td>\n" +
            "       %s" +
            "  </tr>\n" +
            "  <tr>\n" +
            "       %s" +
            "  </tr>\n" +
            "  <tr>\n" +
            "    <td class=\"tg-baqh\" rowspan=\"2\"><br>Looser</td>\n" +
            "       %s" +
            "  </tr>\n" +
            "  <tr>\n" +
            "       %s" +
            "  </tr>\n" +
            "</table>" +
            "</html>";
}
