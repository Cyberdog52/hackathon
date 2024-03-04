import PersonView from "./components/PersonView";
import DiagramView from "./components/DiagramView";
import JokeView from "./components/JokeView";

import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
import "react-tabs/style/react-tabs.css";

function App() {
  const apiBaseUrl = "https://localhost:7017";

  return (
    <>
      <h1 className="m-3">Hackathon React Demo and Template</h1>
      <p className="m-3">
        See README.md for more details.
      </p>
      <hr />
      <Tabs>
        <TabList>
          <Tab>Persons</Tab>
          <Tab>Diagrams</Tab>
          <Tab>Jokes</Tab>
        </TabList>
        <TabPanel>
          <p style={{ marginLeft: 20 }}>
            <i>
              Gets a list of names from the backend and requests additional
              details based on the selected name.
              <br />
              The data is fetched from the backend using GET requests (names,
              details) and static files (images).
              <br/>
              Of course they are anime characters...
            </i>
          </p>
          <hr />
          <PersonView
            apiUrl={apiBaseUrl}
            viewWidth={200}
            viewHeight={200}
            viewMargin={50}
            contentPadding={20}
          />
        </TabPanel>
        <TabPanel>
          <p style={{ marginLeft: 20 }}>
            <i>
              Gets a list of diagrams from the backend and requests a rendering
              script based on the selected diagram.
              <br />
              The diagrams are rendered client-side using a human-readable
              script (
              <a
                href="https://mermaid.js.org/"
                target="_blank"
                title="Mermaid: diagramming and charting tool"
              >
                mermaid.js
              </a>
              ).
              <br />
              There is also an{" "}
              <a href="https://mermaid.live/" target="_blank">online playground</a>.
            </i>
          </p>
          <hr />
          <DiagramView
            apiUrl={apiBaseUrl}
            viewWidth={800}
            viewHeight={800}
            viewMargin={50}
            contentPadding={20}
          />
        </TabPanel>
        <TabPanel>
          <p style={{ marginLeft: 20 }}>
            <i>
              Gets random jokes from the backend and displays them.
              <br />
              The jokes itself are fetched by the backend from an other
              third-party API (
              <a
                href="https://github.com/15Dkatz/official_joke_api"
                target="_blank"
              >
                official_joke_api
              </a>
              ).
              <br />
              There are general, programming, knock-knock, and a few dad jokes (there's no guarantee that all of them are funny...)
            </i>
          </p>
          <hr />
          <JokeView apiUrl={apiBaseUrl} viewMargin={50} contentPadding={20} />
        </TabPanel>
      </Tabs>
    </>
  );
}

export default App;
