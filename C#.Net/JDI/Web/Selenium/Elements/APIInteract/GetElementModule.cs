﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.RegularExpressions;
using Epam.JDI.Commons;
using Epam.JDI.Core;
using Epam.JDI.Core.Interfaces.Base;
using Epam.JDI.Core.Settings;
using Epam.JDI.Web.Selenium.DriverFactory;
using Epam.JDI.Web.Settings;
using OpenQA.Selenium;
using static Epam.JDI.Core.Settings.JDISettings;

namespace Epam.JDI.Web.Selenium.Elements.APIInteract
{
    public class GetElementModule : IAvatar
    {
        public WebBaseElement Element { get; set; }
        public By ByLocator;
        public WebBaseElement RootElement;
        public string DriverName { get; set; }

        public IWebDriver WebDriver 
            => WebSettings.WebDriverFactory.GetDriver(DriverName);
        public Func<IWebElement, bool> LocalElementSearchCriteria;

        public GetElementModule()
        {
            DriverName = JDISettings.DriverFactory.CurrentDriverName;
        }

        public Timer Timer => new Timer(Timeouts.CurrentTimeoutSec*1000);
        public bool HasLocator => ByLocator != null;
        private IWebElement _webElement;
        private List<IWebElement> _webElements;

        public IWebElement WebElement
        {
            get
            {
                Logger.Debug($"Get Web Element: {Element}");
                var element = Timer.GetResultByCondition(GetWebElemetAction, el => el != null);
                Logger.Debug("OneElement found");
                return element;
            }
            set { _webElement = value; }
        }

        public List<IWebElement> WebElements
        {
            get
            {
                Logger.Debug($"Get Web Elements: {Element}");
                var elements = GetWebElemetsAction();
                Logger.Debug($"Found {elements.Count} elements");
                return elements;
            }
            set { _webElements = value; }
        }
        public T FindImmediately<T>(Func<T> func, T ifError)
        {
            Element.SetWaitTimeout(0);
            var temp = Element.WebAvatar.LocalElementSearchCriteria;
            Element.WebAvatar.LocalElementSearchCriteria = el => true;
            T result;
            try { result = func.Invoke(); }
            catch { result = ifError; }
            Element.WebAvatar.LocalElementSearchCriteria = temp;
            Element.RestoreWaitTimeout();
            return result;
        }

        private IWebElement GetWebElemetAction()
        {
            if (_webElement != null)
                return _webElement;
            var timeout = Timeouts.CurrentTimeoutSec;
            var result = GetWebElemetsAction();
            switch (result.Count)
            {
                case 0:
                    throw Exception($"Can't find Element '{Element}' during {timeout} seconds");
                case 1:
                    return result[0];
                default:
                    throw Exception(
                        $"Find {result.Count} elements instead of one for Element '{Element}' during {timeout} seconds");
            }

        }
        private List<IWebElement> GetWebElemetsAction()
        {
            if (_webElements != null)
                return _webElements;
            var result = Timer.GetResultByCondition(
                    SearchElements,
                    els => els.Count(GetSearchCriteria) > 0);
            Timeouts.DropTimeouts();
            if (result == null)
                throw Exception("Can't get Web Elements");
            return result.Where(GetSearchCriteria).ToList();

        }

        private ISearchContext SearchContext(Object element)
        {
            object p;
            WebBaseElement el;
            if (element == null || (el = element as WebBaseElement) == null || (p = el.Parent) == null)
                return WebDriver.SwitchTo().DefaultContent();
            var searchContext = SearchContext(p);
            return el.Locator != null
                ? searchContext.FindElement(CorrectXPath(el.Locator))
                : searchContext;
        }

        public GetElementModule SearchAll()
        {
            LocalElementSearchCriteria = el => el != null;
            return this;
        }
        private List<IWebElement> SearchElements()
        {
            return SearchContext(Element.Parent).FindElements(CorrectXPath(ByLocator)).ToList();
        }
        private By CorrectXPath(By byValue)
        {
            return byValue.ToString().Contains("By.xpath: //")
                    ? byValue.GetByFunc()(new Regex("//").Replace(byValue.GetByLocator(), "./", 1))
                    : byValue;
        }

        private Func<IWebElement, bool> GetSearchCriteria 
            => LocalElementSearchCriteria ?? WebSettings.WebDriverFactory.ElementSearchCriteria;
        
    }
}