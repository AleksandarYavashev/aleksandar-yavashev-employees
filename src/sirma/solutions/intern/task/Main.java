package sirma.solutions.intern.task;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.lang.Long;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        System.out.println("Please provide path to input file: ");
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        String filePath = cin.readLine();
        cin.close();


        String line = null;
        List<String> record = null;
        List<Record> tempRecordList = null;
        Map projectToList = new HashMap<String, List<Record>>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            while ((line = br.readLine()) != null) {
                record = split(line);

                Date beginningDate, endDate;

                if (record.get(2).equals("NULL")) {
                    beginningDate = new Date();
                } else {
                    beginningDate = sdf.parse(record.get(2));
                }

                if (record.get(3).equals("NULL")) {
                    endDate = new Date();
                } else {
                    endDate = sdf.parse(record.get(3));
                }


                //Array for Project exist
                if (projectToList.containsKey(record.get(1))) {
                    tempRecordList = (List<Record>) projectToList.get(record.get(1));

                    tempRecordList.add(new Record(record.get(0), record.get(1), beginningDate, endDate));
                } else {
                    tempRecordList = new ArrayList<>();
                    tempRecordList.add(new Record(record.get(0), record.get(1), beginningDate, endDate));

                    projectToList.put(record.get(1), new ArrayList<Record>(tempRecordList));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (ParseException pE) {
            pE.printStackTrace();
            throw pE;
        }

        Map<String, Long> coupleToDays = new HashMap<>();

        Collection<List<Record>> listCollection = projectToList.values();
        for (List<Record> elem : listCollection) {
            calculateTime(elem, coupleToDays);
        }

        String maxCouple = getMaxCouple(coupleToDays);

        String firstID = maxCouple.substring(0, maxCouple.length() / 2);
        String secondID = maxCouple.substring(maxCouple.length() / 2);

        System.out.printf("firstID = " + firstID + " and secondID = " + secondID);
    }


    public static List<String> split(String str) {
        return Stream.of(str.split(", "))
                .map(elem -> new String(elem))
                .collect(Collectors.toList());
    }

    public static Long getDifference(Date a, Date b) {
        long diffInMillies = Math.abs(a.getTime() - b.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }

    public static void calculateTime(List<Record> list, Map<String, Long> map) {
        int size = list.size();
        long difference = 0;

        Date beginA, beginB, endA, endB;
        for (int i = 0; i < size - 1; ++i) {
            for (int j = i + 1; j < size; ++j) {
                beginA = list.get(i).getBeginning();
                beginB = list.get(j).getBeginning();

                endA = list.get(i).getEnd();
                endB = list.get(j).getEnd();

                if (beginA.compareTo(beginB) <= 0) {
                    if (endB.compareTo(endA) <= 0) {
                        difference = getDifference(beginB, endB);
                    }

                    if (endA.compareTo(beginB) <= 0) {
                        difference = 0;
                    }

                    if ((beginB.compareTo(endA) <= 0) && (endA.compareTo(endB) <= 0)) {
                        difference = getDifference(beginB, endA);
                    }
                } else {
                    if (endA.compareTo(endB) <= 0) {
                        difference = getDifference(beginA, endA);
                    }

                    if (endB.compareTo(beginA) <= 0) {
                        difference = 0;
                    }

                    if ((beginA.compareTo(endB) <= 0) && (endB.compareTo(endA) <= 0)) {
                        difference = getDifference(beginA, endB);
                    }
                }

                String toAdd;
                if (list.get(j).getEmployerID().compareTo(list.get(i).getEmployerID()) <= 0) {
                    toAdd = list.get(j).getEmployerID().concat(list.get(i).getEmployerID());
                } else {
                    toAdd = list.get(i).getEmployerID().concat(list.get(j).getEmployerID());
                }

                if (map.containsKey(toAdd)) {
                    difference += map.get(toAdd);
                }
                map.put(toAdd, difference);
            }
        }

    }

    public static String getMaxCouple(Map<String, Long> map) {
        long max = 0;
        String result = null;

        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (entry.getValue() >= max) {
                max = entry.getValue();
                result = entry.getKey();
            }
        }
        return result;
    }
}

