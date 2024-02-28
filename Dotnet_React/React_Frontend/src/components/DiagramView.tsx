import mermaid from "mermaid";
import { useEffect, useState } from "react";
import { fetchDiagramNames, fetchDiagram } from "./DiagramApi";

interface DiagramViewProps {
  apiUrl: string;
  imageWidth?: number;
  imageHeight?: number;
}

export default function DiagramView({
  apiUrl,
  imageWidth,
  imageHeight
}: DiagramViewProps) {
  const [diagramNames, setDiagramNames] = useState<string[]>([]);
  const [selectedName, setSelectedName] = useState<string>("");
  const [selectedDiagram, setSelectedDiagram] = useState<any>([]);
  const [diagramSvg, setDiagramSvg] = useState<string>("");

  useEffect(() => {
    fetchDiagramNames(apiUrl, (x) => {
      console.log("Available diagram names: ", x);
      setDiagramNames(x);
    });
  }, []);

  useEffect(() => {
    fetchDiagram(apiUrl, selectedName, (x) => {
      console.log("Selected diagram data: ", x);
      setSelectedDiagram(x);
    });
  }, [selectedName]);

  useEffect(() => {
    mermaid.render("mermaid", selectedDiagram.script).then((x) => {
      console.log("Rendered diagram: ", x);
      setDiagramSvg(btoa(x.svg));
    });
  }, [selectedDiagram]);

  const svgData = "data:image/svg+xml;base64," + diagramSvg;
  const imgWidth = diagramSvg.length === 0 ? 0 : imageWidth;
  const imgHeight = diagramSvg.length === 0 ? 0 : imageHeight;

  return (
    <div>
      <div className="btn-group-vertical" role="group">
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
                setSelectedName(item);
              }}
            >
              {item}
            </label>
          </>
        ))}
      </div>
      <a href={svgData}>
        <img src={svgData} height={imgHeight} width={imgWidth} />
      </a>
      <div id="mermaid" />
    </div>
  );
}
