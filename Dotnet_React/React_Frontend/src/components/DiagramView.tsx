import mermaid from "mermaid";
import { useEffect, useState } from "react";
import { fetchDiagramNames, fetchDiagram } from "./DiagramApi";

interface DiagramViewProps {
  apiUrl: string;
  viewWidth?: number;
  viewHeight?: number;
  viewMargin?: number;
  contentPadding?: number;
}

export default function DiagramView({
  apiUrl,
  viewWidth,
  viewHeight,
  viewMargin,
  contentPadding,
}: DiagramViewProps) {
  const [diagramNames, setDiagramNames] = useState<string[]>([]);
  const [selectedDiagramName, setSelectedDiagramName] = useState<string>("");
  const [selectedDiagramData, setSelectedDiagramData] = useState<any>([]);
  const [renderedDiagram, setRenderedDiagram] = useState<string>("");

  useEffect(() => {
    fetchDiagramNames(apiUrl, (x) => {
      console.log("Available diagram names: ", x);
      setDiagramNames(x);
    });
  }, []);

  useEffect(() => {
    fetchDiagram(apiUrl, selectedDiagramName, (x) => {
      console.log("Selected diagram data: ", x);
      setSelectedDiagramData(x);
    });
  }, [selectedDiagramName]);

  useEffect(() => {
    mermaid.render("mermaid", selectedDiagramData.script).then((x) => {
      console.log("Rendered diagram: ", x);
      setRenderedDiagram(btoa(x.svg));
    });
  }, [selectedDiagramData]);

  const hideDiagram = renderedDiagram.length === 0;
  const svgData = "data:image/svg+xml;base64," + renderedDiagram;

  return (
    <div className="grid" style={{ margin: viewMargin }}>
      <div className="row">
        <div className="col-5 btn-group" role="group">
          {diagramNames.map((item) => (
            <>
              <input
                type="radio"
                className="btn-check"
                name="vbtn-radio"
                id={item}
              />
              <label
                className="btn btn-outline-secondary"
                htmlFor={item}
                id={item}
                onClick={() => {
                  console.log("Selected diagram name:", item);
                  setSelectedDiagramName(item);
                }}
              >
                {item}
              </label>
            </>
          ))}
        </div>
      </div>
      <div
        className="row"
        style={
          hideDiagram ? { display: "none" } : { marginTop: contentPadding }
        }
      >
        <a href={svgData}>
          <img
            src={svgData}
            style={{ maxWidth: viewWidth, maxHeight: viewHeight }}
          />
        </a>
      </div>
      <div id="mermaid" />
    </div>
  );
}
