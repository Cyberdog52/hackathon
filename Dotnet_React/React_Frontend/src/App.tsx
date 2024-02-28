import { Tab, initMDB } from "mdb-ui-kit";
import PersonView from "./components/PersonView";
import DiagramView from "./components/DiagramView";
import JokeView from "./components/JokeView";

function App() {
  const apiBaseUrl = "https://localhost:7017";

  initMDB({ Tab });

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
          <p style={{ marginLeft: 20 }}>
            <i>xxx</i>
          </p>
          <PersonView
            apiUrl={apiBaseUrl}
            viewWidth={200}
            viewHeight={200}
            viewMargin={50}
            contentPadding={50}
          />
        </div>
        <div
          className="tab-pane fade"
          id="tab_diagram_content"
          role="tabpanel"
          aria-labelledby="tab_diagram_content"
        >
          <p style={{ marginLeft: 20 }}>
            <i>xxx</i>
          </p>
          <DiagramView
            apiUrl={apiBaseUrl}
            viewWidth={1000}
            viewHeight={500}
            viewMargin={50}
            contentPadding={50}
          />
        </div>
        <div
          className="tab-pane fade"
          id="tab_jokes_content"
          role="tabpanel"
          aria-labelledby="tab_jokes_content"
        >
          <p style={{ marginLeft: 20 }}>
            <i>xxx</i>
          </p>
          <JokeView apiUrl={apiBaseUrl} viewMargin={50} contentPadding={20} />
        </div>
      </div>
    </>
  );
}

export default App;
