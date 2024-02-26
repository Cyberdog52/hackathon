import { useState } from "react";

interface PersonListProps {
  names: string[];
  className?: string;
  onNameSelected: (item: string | undefined) => void;
}

export default function PersonList({
  names,
  className,
  onNameSelected,
}: PersonListProps) {
  const [selectedIndex, setSelectedIndex] = useState(-1);

  if (names.length === 0) {
    return (
      <div className={className}>
        <p>No persons available</p>
      </div>
    );
  }

  const usedClassName =
    "grid" +
    (className == undefined || className.length === 0 ? "" : " " + className);

  return (
    <div className={usedClassName}>
      <ul className="list-group">
        {names.map((item, index) =>(
          <li
            className={
              selectedIndex === index
                ? "list-group-item active"
                : "list-group-item"
            }
            key={item}
            onClick={(e) => {
              if(selectedIndex === index)
              {
                console.log("Person name change value:", undefined);
                console.log("Person name change index:", -1);
                setSelectedIndex(-1);
                onNameSelected(undefined);
              }
              else{
                console.log("Person name change value:", item);
                console.log("Person name change index:", index);
                setSelectedIndex(index);
                onNameSelected(item);
              }
            }}
          >
            {item}
          </li>
        ))}
      </ul>
    </div>
  );
}
