import { useEffect, useState } from "react";
import { Tab, initMDB } from "mdb-ui-kit";
import { fetchPersonNames, fetchPersonDetails } from "./components/PersonApi";
import PersonList from "./components/PersonList";
import PersonDetails from "./components/PersonDetails";
import DiagramView from "./components/DiagramView";
import JokeView from "./components/JokeView";

function App() {
  const [personNames, setPersonNames] = useState<string[]>([]);
  const [selectedPersonName, setSelectedPersonName] = useState<
    string | undefined
  >(undefined);
  const [selectedPersonDetails, setSelectedPersonDetails] = useState<any>();
  const [diagramNames, setDiagramNames] = useState<string[]>([]);

  const apiBaseUrl = "https://localhost:7017";

  initMDB({ Tab });

  useEffect(() => {
    fetchPersonNames(apiBaseUrl, (x) => setPersonNames(x));
  }, []);

  useEffect(() => {
    if (selectedPersonName == undefined) {
      console.log("Selected person name: undefined");
      setSelectedPersonDetails(undefined);
      return;
    }
    fetchPersonDetails(apiBaseUrl, selectedPersonName!, (x) => {
      console.log("Selected person name: ", x);
      setSelectedPersonDetails(x);
    });
  }, [selectedPersonName]);

  return (
    <>
      <h1 className="m-3">Hackathon .NET / React demo and template</h1>
      <ul className="nav nav-tabs mb-3" id="t_nav" role="tablist">
        <li className="nav-item" role="presentation">
          <a
            data-mdb-tab-init
            className="nav-link active"
            id="tab_persons_nav"
            href="#tab_persons_content"
            role="tab"
            aria-controls="tab_persons_content"
            aria-selected="true"
          >
            <button className="btn">Persons</button>
          </a>
        </li>
        <li className="nav-item" role="presentation">
          <a
            data-mdb-tab-init
            className="nav-link"
            id="tab_diagram_nav"
            href="#tab_diagram_content"
            role="tab"
            aria-controls="tab_diagram_content"
            aria-selected="false"
          >
            <button className="btn">Diagrams</button>
          </a>
        </li>
        <li className="nav-item" role="presentation">
          <a
            data-mdb-tab-init
            className="nav-link"
            id="tab_jokes_nav"
            href="#tab_jokes_content"
            role="tab"
            aria-controls="tab_jokes_content"
            aria-selected="false"
          >
            <button className="btn">Jokes</button>
          </a>
        </li>
      </ul>
      <div className="tab-content" id="t_content">
        <div
          className="tab-pane fade show active"
          id="tab_persons_content"
          role="tabpanel"
          aria-labelledby="tab_persons_content"
        >
          <div className="grid p-0 mx-3">
            <div className="row">
              <div className="col-2 p-0">
                <PersonList
                  names={personNames}
                  onNameSelected={setSelectedPersonName}
                />
              </div>
              <div className="col p-3 ms-3 border rounded">
                <PersonDetails
                  details={selectedPersonDetails}
                  imageSize={100}
                />
              </div>
            </div>
          </div>
        </div>
        <div
          className="tab-pane fade"
          id="tab_diagram_content"
          role="tabpanel"
          aria-labelledby="tab_diagram_content"
        >
          <DiagramView apiUrl={apiBaseUrl} imageWidth={300} imageHeight={300} />
        </div>
        <div
          className="tab-pane fade"
          id="tab_jokes_content"
          role="tabpanel"
          aria-labelledby="tab_jokes_content"
        >
          <JokeView apiUrl={apiBaseUrl} viewMargin={30} contentPadding={10}/>
        </div>
      </div>
    </>
  );
}

export default App;
