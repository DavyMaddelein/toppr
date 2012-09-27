package be.proteomics.pprIA.general.util;

import be.proteomics.pprIA.search.FoundProtein;
import be.proteomics.pprIA.search.FoundProcessingSite;
import be.proteomics.pprIA.general.SearchOptions;
import be.proteomics.pprIA.general.protein_info.Domain;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Niklaas Colaert
 * Date: 28-nov-2008
 * Time: 8:57:42
 * To change this template use File | Settings | File Templates.
 */
public class NavigationProteinBar {
    private Vector iProcessingSites;
    private FoundProtein iProtein;

    public NavigationProteinBar(Vector aProcessingSites, FoundProtein aProtein){
        iProcessingSites = aProcessingSites;
        iProtein = aProtein;
    }

    public String getNavigationBar(){
         String html = "";
        int[] sitesLocation = new int[iProcessingSites.size()];
        Vector treatments = new Vector();
        for (int i = 0; i < iProcessingSites.size(); i++) {
            FoundProcessingSite site = (FoundProcessingSite) iProcessingSites.get(i);
            sitesLocation[i] = site.getPosition();
            treatments.add(site.getTreatments());
        }

        //sort
        int value;
        int position;
        for (int i = 0; i < sitesLocation.length; i++) {
            value = sitesLocation[i];
            String[] valueTreat = (String[]) treatments.get(i);
            position = i;
            while ((position > 0) && (sitesLocation[position - 1] < value)) {
                sitesLocation[position] = sitesLocation[position - 1];
                treatments.set(position, treatments.get(position - 1));
                position--;
            }
            sitesLocation[position] = value;
            treatments.set(position, valueTreat);

        }

        double seqLength = iProtein.getSequence().length();
        double figAaLength = 924.0 / seqLength;
        Vector pictureElements = new Vector();
        for (int i = 0; i < sitesLocation.length; i++) {
            if (i == 0) {
                int length = (int) ((figAaLength * (seqLength - sitesLocation[i])) - 1); //-3 to make the splice site picture a bit bigger
                String link = "noLink";
                SearchOptions.PictureElement element = new SearchOptions.PictureElement(length, "normale", link, "normale.JPG", 0, 0);
                pictureElements.add(element);
            }
            if (sitesLocation.length == 1 || i == (sitesLocation.length - 1)) {
                int length = 5;
                String link = "noLink";
                String[] treat = (String[]) treatments.get(i);
                String treatment = "";
                for (int j = 0; j < treat.length - 1; j++) {
                    treatment = treatment + treat[j] + ", ";
                }
                treatment = treatment + treat[(treat.length - 1)];
                SearchOptions.PictureElement element;
                if(treat.length == 1){
                    element = new SearchOptions.PictureElement(length, treatment, link, "site.jpg", sitesLocation[i], sitesLocation[i]);
                }  else {
                    element = new SearchOptions.PictureElement(length, treatment, link, "site2.jpg", sitesLocation[i], sitesLocation[i]);
                }
                element.setTreatments(treat);
                element.setPosition(sitesLocation[i] - 1);
                pictureElements.add(element);

                int lengthNorm = (int) ((figAaLength * sitesLocation[i]) - 5);
                String linka = "noLink";
                SearchOptions.PictureElement elementNorm = new SearchOptions.PictureElement(lengthNorm, "normale", linka, "normale.JPG", 0, 0);
                pictureElements.add(elementNorm);
            } else {

                int length = 5;
                String link = "noLink";
                String[] treat = (String[]) treatments.get(i);
                String treatment = "";
                for (int j = 0; j < treat.length - 1; j++) {
                    treatment = treatment + treat[j] + ", ";
                }
                treatment = treatment + treat[(treat.length - 1)];
                SearchOptions.PictureElement element;
                if(treat.length == 1){
                    element = new SearchOptions.PictureElement(length, treatment, link, "site.jpg", sitesLocation[i], sitesLocation[i]);
                }  else {
                    element = new SearchOptions.PictureElement(length, treatment, link, "site2.jpg", sitesLocation[i], sitesLocation[i]);
                }

                element.setTreatments(treat);
                element.setPosition(sitesLocation[i] - 1);
                pictureElements.add(element);

                int lengthNorm = (int) ((figAaLength * (sitesLocation[i] - sitesLocation[i + 1])) - 5);
                if (lengthNorm > 0) {
                    String linka = "noLink";
                    SearchOptions.PictureElement elementNorm = new SearchOptions.PictureElement(lengthNorm, "normale", linka, "normale.JPG", 0, 0);
                    pictureElements.add(elementNorm);
                }


            }
        }

        for (int i = 0; i < pictureElements.size(); i++) {
            SearchOptions.PictureElement ele = (SearchOptions.PictureElement) pictureElements.get(i);
            if (!ele.getName().equalsIgnoreCase("normale")) {
                String eleInfo = "Treatment: " + ele.getName() + " Site: " + ele.getStart();
                String picture = "<img style=\"cursor:pointer;\" src =\"images/" + ele.getPicture() + "\" onmouseover=\"replaceProteinInfo('" + iProtein.getSpAccession() + "','" + eleInfo + "')\"";
                picture = picture + "\" onclick=\"";
                for(int j = 0; j<ele.getTreatments().length; j ++){
                    picture = picture + "showHidePosition('" + iProtein.getSpAccession() + "_" + ele.getTreatments()[j] +"."+ele.getPosition() + "','img'),";
                }
                picture = picture.substring(0,picture.lastIndexOf(",")) + "\"";
                picture = picture + " width =\"" + ele.getLength() + "\" height =\"46\"/>";
                for(int j = 0; j<ele.getTreatments().length; j ++){
                    picture = picture  + "<span id=\"" + iProtein.getSpAccession() + "_" + ele.getTreatments()[j] +"."+ele.getPosition() +".Navigator\"style=\"display:none;\">none</span>";
                }
                html = picture + html;
            } else {
                html = "<img src =\"images/" + ele.getPicture() + "\" width =\"" + ele.getLength() + "\" height =\"46\"/>" + html;
            }
        }
        html = "<img src=\"images/start.JPG\" height =\"46\"/>" + html + "<img src=\"images/end.JPG\" height =\"46\"/>";
        return html;
    }


    public String getDomainHtml(Domain[] positionsUnOrdered, String db) {
        //sort
        String sequence = iProtein.getSequence();
        String pictureHtml = "";
        Domain[] positionsOrdered = orderPositions(positionsUnOrdered);
        double seqLength = sequence.length();
        double figAaLength = 924.0 / seqLength;
        Vector pictureElements = new Vector();
        if (positionsOrdered.length == 0) {
            pictureHtml = "No " +db+ " domains found";
        }
        for (int i = 0; i < positionsOrdered.length; i++) {
            if (i == 0) {
                int length = (int) (figAaLength * (sequence.length() - positionsOrdered[i].getEnd()));
                String link = "noLink";
                SearchOptions.PictureElement element = new SearchOptions.PictureElement(length, "normale", link, "normale.JPG", 0, 0);
                pictureElements.add(element);
            }
            if (positionsOrdered.length == 1 || i == (positionsOrdered.length - 1)) {
                int length = (int) (figAaLength * (positionsOrdered[i].getEnd() - positionsOrdered[i].getStart()));
                SearchOptions.PictureElement element = new SearchOptions.PictureElement(length, positionsOrdered[i].getLabel(), positionsOrdered[i].getLink(), "domain.JPG", positionsOrdered[i].getStart(), positionsOrdered[i].getEnd());
                pictureElements.add(element);

                int lengthNorm = (int) (figAaLength * (positionsOrdered[i].getStart()));
                String link = "noLink";
                SearchOptions.PictureElement elementNorm = new SearchOptions.PictureElement(lengthNorm, "normale", link, "normale.JPG", 0, 0);
                pictureElements.add(elementNorm);
            } else {

                if (positionsOrdered[i].getStart() < positionsOrdered[i + 1].getEnd()) {
                    //double domain
                    if (positionsOrdered[i].getStart() > positionsOrdered[i + 1].getStart()) {
                        int lengthNorm = (int) (figAaLength * (positionsOrdered[i].getEnd() - positionsOrdered[i + 1].getEnd()));
                        SearchOptions.PictureElement elementNorm = new SearchOptions.PictureElement(lengthNorm, positionsOrdered[i].getLabel(), positionsOrdered[i].getLink(), "domain.JPG", positionsOrdered[i].getStart(), positionsOrdered[i].getEnd());
                        elementNorm.setDoubleDomain(true);
                        elementNorm.setDoubleRight(true);
                        pictureElements.add(elementNorm);

                        int lengthDouble = (int) (figAaLength * (positionsOrdered[i + 1].getEnd() - positionsOrdered[i].getStart()));
                        String link = "noLink";
                        SearchOptions.PictureElement elementDouble = new SearchOptions.PictureElement(lengthDouble, "double", link, "double.JPG", 0, 0);
                        elementDouble.setDoubleDomain(true);
                        elementDouble.setDoubleMiddle(true);
                        pictureElements.add(elementDouble);

                        int lengthD2 = (int) (figAaLength * (positionsOrdered[i].getStart() - positionsOrdered[i + 1].getStart()));
                        SearchOptions.PictureElement elementD2 = new SearchOptions.PictureElement(lengthD2, positionsOrdered[i + 1].getLabel(), positionsOrdered[i + 1].getLink(), "domain.JPG", positionsOrdered[i].getStart(), positionsOrdered[i].getEnd());
                        elementD2.setDoubleDomain(true);
                        elementD2.setDoubleLeft(true);
                        pictureElements.add(elementD2);
                        if ((i + 1) == (positionsOrdered.length - 1)) {
                            int lengthNorma = (int) (figAaLength * (positionsOrdered[i + 1].getStart()));
                            String linka = "noLink";
                            SearchOptions.PictureElement elementNorma = new SearchOptions.PictureElement(lengthNorma, "normale", linka, "normale.JPG", 0, 0);
                            pictureElements.add(elementNorma);
                        } else {
                            int lengthNormaa = (int) (figAaLength * (positionsOrdered[i + 1].getStart() - positionsOrdered[i + 2].getEnd()));
                            if (lengthNormaa > 0) {
                                String linkaa = "noLink";
                                SearchOptions.PictureElement elementNormaa = new SearchOptions.PictureElement(lengthNormaa, "normale", linkaa, "normale.JPG", 0, 0);
                                pictureElements.add(elementNormaa);
                            }
                        }
                    } else {
                        //domain in domain
                        int lengthNorm = (int) (figAaLength * (positionsOrdered[i].getEnd() - positionsOrdered[i + 1].getEnd()));
                        SearchOptions.PictureElement elementNorm = new SearchOptions.PictureElement(lengthNorm, positionsOrdered[i].getLabel(), positionsOrdered[i].getLink(), "domain.JPG", positionsOrdered[i].getStart(), positionsOrdered[i].getEnd());
                        elementNorm.setDoubleDomain(true);
                        elementNorm.setDoubleRight(true);
                        pictureElements.add(elementNorm);

                        int lengthDouble = (int) (figAaLength * (positionsOrdered[i + 1].getEnd() - positionsOrdered[i + 1].getStart()));
                        SearchOptions.PictureElement elementDouble = new SearchOptions.PictureElement(lengthDouble, positionsOrdered[i + 1].getLabel(), positionsOrdered[i + 1].getLink(), "double.JPG", positionsOrdered[i + 1].getStart(), positionsOrdered[i + 1].getEnd());
                        elementDouble.setDoubleDomain(true);
                        elementDouble.setDoubleMiddle(true);
                        pictureElements.add(elementDouble);

                        int lengthD2 = (int) (figAaLength * (positionsOrdered[i + 1].getStart() - positionsOrdered[i].getStart()));
                        SearchOptions.PictureElement elementD2 = new SearchOptions.PictureElement(lengthD2, positionsOrdered[i].getLabel(), positionsOrdered[i].getLink(), "domain.JPG", positionsOrdered[i].getStart(), positionsOrdered[i].getEnd());
                        elementD2.setDoubleDomain(true);
                        elementD2.setDoubleLeft(true);
                        pictureElements.add(elementD2);
                        if ((i + 1) == (positionsOrdered.length - 1)) {
                            int lengthNorma = (int) (figAaLength * (positionsOrdered[i].getStart()));
                            String linka = "noLink";
                            SearchOptions.PictureElement elementNorma = new SearchOptions.PictureElement(lengthNorma, "normale", linka, "normale.JPG", 0, 0);
                            pictureElements.add(elementNorma);
                        } else {
                            int lengthNormaa = (int) (figAaLength * (positionsOrdered[i].getStart() - positionsOrdered[i + 2].getEnd()));
                            if (lengthNormaa > 0) {
                                String linkaa = "noLink";
                                SearchOptions.PictureElement elementNormaa = new SearchOptions.PictureElement(lengthNormaa, "normale", linkaa, "normale.JPG", 0, 0);
                                pictureElements.add(elementNormaa);
                            }
                        }
                    }

                    i = i + 1;
                } else {

                    int length = (int) (figAaLength * (positionsOrdered[i].getEnd() - positionsOrdered[i].getStart()));
                    SearchOptions.PictureElement element = new SearchOptions.PictureElement(length, positionsOrdered[i].getLabel(), positionsOrdered[i].getLink(), "domain.JPG", positionsOrdered[i].getStart(), positionsOrdered[i].getEnd());
                    pictureElements.add(element);
                    int lengthNorm = (int) (figAaLength * (positionsOrdered[i].getStart() - positionsOrdered[i + 1].getEnd()));
                    if (lengthNorm > 0) {
                        String link = "noLink";
                        SearchOptions.PictureElement elementNorm = new SearchOptions.PictureElement(lengthNorm, "normale", link, "normale.JPG", 0, 0);
                        pictureElements.add(elementNorm);
                    }
                }
            }
        }


        for (int i = 0; i < pictureElements.size(); i++) {
            SearchOptions.PictureElement ele = (SearchOptions.PictureElement) pictureElements.get(i);
            if (!ele.getName().equalsIgnoreCase("normale")) {
                //double domain?
                if (ele.isDoubleDomain()) {
                    if (ele.isDoubleRight()) {
                        if (!ele.getName().equalsIgnoreCase("double")) {
                            String eleInfo = "Domain (" + db + "): " + ele.getName() + "  Site: " + ele.getStart() + "-" + ele.getEnd();
                            pictureHtml = "<a href=\"" + ele.getLink() + "\" target=\"_blank\"><img src=\"images/endDoubleBox.jpg\"  border=\"0\" height=\"46\"><img src =\"images/box.JPG\" onmouseover=\"replaceProteinInfo('" + iProtein.getSpAccession() + "','" + eleInfo + "')\" width =\"" + (ele.getLength() - 20) + "\" border=\"0\" height =\"46\"/><img src=\"images/endBox.JPG\"  border=\"0\" height=\"46\"></a>" + pictureHtml;

                        } else {
                            pictureHtml = "<a href=\"" + ele.getLink() + "\" target=\"_blank\"><img src=\"images/endDoubleBox.jpg\"  border=\"0\" height=\"46\"><img src =\"images/box.JPG\" width =\"" + (ele.getLength() - 20) + "\" border=\"0\" height =\"46\"/><img src=\"images/endBox.JPG\"  border=\"0\" height=\"46\"></a>" + pictureHtml;
                        }
                    }
                    if (ele.isDoubleMiddle()) {
                        if (!ele.getName().equalsIgnoreCase("double")) {
                            String eleInfo = "Domain (" + db + "): " + ele.getName() + "  Site: " + ele.getStart() + "-" + ele.getEnd();
                            pictureHtml = "<a href=\"" + ele.getLink() + "\" target=\"_blank\"><img src =\"images/doubleBox.jpg\" onmouseover=\"replaceProteinInfo('" + iProtein.getSpAccession() + "','" + eleInfo + "')\" width =\"" + ele.getLength() + "\" border=\"0\" height =\"46\"/></a>" + pictureHtml;

                        } else {
                            pictureHtml = "<a href=\"" + ele.getLink() + "\" target=\"_blank\"><img src =\"images/box.JPG\" width =\"" + ele.getLength() + "\" border=\"0\" height =\"46\"/></a>" + pictureHtml;
                        }
                    }
                    if (ele.isDoubleLeft()) {
                        if (!ele.getName().equalsIgnoreCase("double")) {
                            String eleInfo = "Domain (" + db + "): " + ele.getName() + "  Site: " + ele.getStart() + "-" + ele.getEnd();
                            pictureHtml = "<a href=\"" + ele.getLink() + "\" target=\"_blank\"><img src=\"images/startBox.JPG\"  border=\"0\" height=\"46\"><img src =\"images/box.JPG\" onmouseover=\"replaceProteinInfo('" + iProtein.getSpAccession() + "','" + eleInfo +"')\" width =\"" + (ele.getLength() - 20) + "\" border=\"0\" height =\"46\"/><img src=\"images/startDoubleBox.jpg\"  border=\"0\" height=\"46\"></a>" + pictureHtml;

                        } else {
                            pictureHtml = "<a href=\"" + ele.getLink() + "\" target=\"_blank\"><img src=\"images/startBox.JPG\"  border=\"0\" height=\"46\"><img src =\"images/box.JPG\" width =\"" + (ele.getLength() - 20) + "\" border=\"0\" height=\"46\"/><img src=\"images/startDoubleBox.jpg\"  border=\"0\" height=\"46\"></a>" + pictureHtml;
                        }
                    }
                } else {
                    if (ele.getLength() > 26) {
                        String eleInfo = "Domain (" + db + "): " + ele.getName() + "  Site: " + ele.getStart() + "-" + ele.getEnd();
                        pictureHtml = "<a href=\"" + ele.getLink() + "\" target=\"_blank\"><img src=\"images/startBox.JPG\"  border=\"0\" height=\"46\"><img src =\"images/box.JPG\" onmouseover=\"replaceProteinInfo('" + iProtein.getSpAccession() + "','" + eleInfo + "')\" width =\"" + (ele.getLength() - 26) + "\" border=\"0\" height =\"46\"/><img src=\"images/endBox.JPG\"  border=\"0\" height=\"46\"></a>" + pictureHtml;
                    } else {
                        String eleInfo = "Domain (" + db + "): " + ele.getName() + "  Site: " + ele.getStart() + "-" + ele.getEnd();
                        pictureHtml = "<a href=\"" + ele.getLink() + "\" target=\"_blank\"><img src =\"images/" + ele.getPicture() + "\" onmouseover=\"replaceProteinInfo('" + iProtein.getSpAccession() + "','" + eleInfo + "')\" width =\"" + ele.getLength() + "\" border=\"0\" height=\"46\"/></a>" + pictureHtml;
                    }
                }

            } else {
                pictureHtml = "<img src =\"images/" + ele.getPicture() + "\" width =\"" + ele.getLength() + "\" height =\"46\"/>" + pictureHtml;
            }


        }
        if (!pictureHtml.startsWith("No")) {
            pictureHtml = "<img src=\"images/start.JPG\" height =\"46\"/>" + pictureHtml + "<img src=\"images/end.JPG\" height =\"46\"/>";
        }
        return pictureHtml;
    }


    public Domain[] orderPositions(Domain[] unOrdered) {
        int value;
        int position;
        for (int i = 0; i < unOrdered.length; i++) {
            value = unOrdered[i].getEnd();
            Domain valuePos = unOrdered[i];
            position = i;
            while ((position > 0) && (unOrdered[position - 1].getEnd() < value)) {
                unOrdered[position] = unOrdered[position - 1];
                position--;
            }
            unOrdered[position] = valuePos;
        }
        return unOrdered;
    }
}
