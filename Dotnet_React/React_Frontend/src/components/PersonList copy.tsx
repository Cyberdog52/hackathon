// import Async from "react-async";
// import { useState } from "react";
// import {loadNames} from "./PersonApi";

// interface PersonListProps {
//   names: string[];
//   onSelectItem: (item: string) => void;
// }

// function PersonList({ names, onSelectItem }: PersonListProps) {
//   const [selectedIndex, setSelectedIndex] = useState(-1);
  

//   //const content = loadNames(baseUrl);

//   //const content = new Promise<string[]>(() => ["Megumin", "Dakunesu"]);
  
//   const content = async () => loadNames(baseUrl);

//   return (
//     <ul className="list-group">
//       <Async promiseFn={content} def={[""]}>
//         <Async.Pending>
//         </Async.Pending>
//         <Async.Fulfilled>
//           {(data: string[]) => (
//             <>
//               {data.map((item, index) => (
//                 <li
//                   className={
//                     selectedIndex === index
//                       ? "list-group-item active"
//                       : "list-group-item"
//                   }
//                   key={item}
//                   onClick={e => {
//                     console.log("Person name change event:", e);
//                     setSelectedIndex(index);
//                     onSelectItem(item);
//                   }}
//                 >
//                   {item}
//                 </li>
//               ))}
//             </>
//           )}
//         </Async.Fulfilled>
//         <Async.Rejected>
//           {(error) => `Something went wrong: ${error.message}`}
//         </Async.Rejected>
//       </Async>
//     </ul>
//   );
// }

// export default PersonList;
