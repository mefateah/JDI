package com.epam.jdi.uitests.testing.simple.examples;

import com.epam.commons.map.MapArray;
import com.epam.jdi.uitests.testing.career.common.tests.TestsBase;
import com.epam.jdi.uitests.web.selenium.elements.complex.table.interfaces.ICell;
import com.epam.web.matcher.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jdi.uitests.testing.career.page_objects.site.EpamSite.jobDescriptionPage;
import static com.epam.jdi.uitests.testing.career.page_objects.site.EpamSite.jobListingPage;
import static com.epam.jdi.uitests.web.selenium.elements.complex.table.Column.inColumn;
import static com.epam.jdi.uitests.web.selenium.elements.complex.table.WithValue.withValue;


public class TableExamples extends TestsBase {
    /*@BeforeMethod
    public void before(Method method) {
        jobListingPage.isOpened();
        Assert.isFalse(jobListingPage.jobsList::isEmpty);
    }*/
    @Test
    public void getTableInfoExample() {
        jobListingPage.isOpened();
        Assert.isFalse(jobListingPage.jobsList::isEmpty);
        Assert.areEquals(jobListingPage.jobsList.columns().count(), 4);
        Assert.areEquals(jobListingPage.jobsList.rows().count(), 2);
        Assert.areEquals(jobListingPage.jobsList.getValue(),
            "||X||JOB_NAME|category|location|APPLY||\n" +
            "||1||QA Specialist|Software Test Engineering|St-Petersburg, Russia|apply||\n" +
            "||2||Senior QA Automation Engineer|Software Test Engineering|St-Petersburg, Russia|apply||");
    }

    @Test
    public void searchInTableExample() {
        jobListingPage.isOpened();
        Assert.isFalse(jobListingPage.jobsList::isEmpty);
        jobListingPage.jobsList
            .row(withValue("Senior QA Automation Engineer"), inColumn("JOB_NAME"))
            .get("APPLY").select();

        jobDescriptionPage.checkOpened();
    }
    @Test
    public void searchContainsInTableExample() {
        jobListingPage.isOpened();
        Assert.isFalse(jobListingPage.jobsList::isEmpty);
        jobListingPage.jobsList
                .rowContains("Automation Engineer", inColumn("JOB_NAME"))
                .get("APPLY").select();

        jobDescriptionPage.checkOpened();
    }
    @Test
    public void searchMatchInTableExample() {
        jobListingPage.isOpened();
        Assert.isFalse(jobListingPage.jobsList::isEmpty);
        jobListingPage.jobsList
                .rowMatch(".+ Automation Engineer", inColumn("JOB_NAME"))
                .get("APPLY").select();

        jobDescriptionPage.checkOpened();
    }
    @Test
    public void searchContainsListInTableExample() {
        jobListingPage.isOpened();
        Assert.isFalse(jobListingPage.jobsList::isEmpty);
        MapArray<String, ICell> firstRow = jobListingPage.jobsList.rows(
                "JOB_NAME~=Automation Engineer",
                "category*=.*Test Engineering")
                .first().value;

        Assert.areEquals(firstRow.get("JOB_NAME").getText(), "Senior QA Automation Engineer");
        Assert.areEquals(firstRow.get("category").getText(), "Software Test Engineering");
    }

    @Test
    public void searchByMultiCriteriaInTableExample() {
        jobListingPage.isOpened();
        Assert.isFalse(jobListingPage.jobsList::isEmpty);
        MapArray<String, ICell> firstRow = jobListingPage.jobsList.rows(
                "JOB_NAME=Senior QA Automation Engineer",
                "category=Software Test Engineering")
                .first().value;

        Assert.areEquals(firstRow.get("JOB_NAME").getText(), "Senior QA Automation Engineer");
        Assert.areEquals(firstRow.get("category").getText(), "Software Test Engineering");
    }
}
