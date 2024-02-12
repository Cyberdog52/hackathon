

interface NamesProps {
  names: string[];
}

export function Names({names}: NamesProps) {
  return (
    <>
      <ul className="list-group">
        {names.map((name) => (
          <li
            className={"list-group-item"}
            key={name}
          >
            {name}
          </li>
        ))}
      </ul>
    </>
  );
}

// // export class ApiCaller extends React.Component {
// //   names: string[] = [];

// //   constructor(props: {} | Readonly<{}>) {
// //     super(props);

// //     this.state = { names: [""] };
// //   }

// //   async readNames() {
// //     const caller = axios.create({
// //       baseURL: "https://localhost:7017/",
// //       timeout: 1000,
// //     });

// //     // Make a request for a user with a given ID
// //     const result = caller
// //       .get("/persons/names")
// //       .then((response) => {
// //         console.log(response);
// //         this.names = response.data.data;
// //       })
// //       .catch(function (error) {
// //         console.log(error);
// //       })
// //       .finally(function () {});

// //       await result;

// //       return this.names;
// //   }

// //   getNames() {
// //     return this.names;
// //   }
// // }

// // interface IGetDetails {
// //   name: string;
// // }

// // function GetNames() : object {

// //     const caller = axios.create({
// //       baseURL: "https://localhost:7017/",
// //       timeout: 1000,
// //     });

// //     // Make a request for a user with a given ID
// //     let result = caller
// //       .get("/persons/names")
// //       .then(function (response) {
// //         // handle success
// //         console.log(response);
// //         return response.data;
// //       })
// //       .catch(function (error) {
// //         // handle error
// //         console.log(error);
// //         return error;
// //       })
// //       .finally(function () {
// //         // always executed
// //       });

// //       return result;
// // }

// // export function GetDetails({ name }: IGetDetails): string {
// //   const caller = axios.create({
// //     baseURL: "https://localhost:7017/",
// //     timeout: 1000,
// //   });

// //   // Make a request for a user with a given ID
// //   caller
// //     .get("/" + name + "/details")
// //     .then(function (response) {
// //       // handle success
// //       console.log(response);
// //       return response;
// //     })
// //     .catch(function (error) {
// //       // handle error
// //       console.log(error);
// //       return error;
// //     })
// //     .finally(function () {
// //       // always executed
// //     });
// //   return "";
// // }

// export class ApiCaller extends React.Component {
//   constructor(props: {} | Readonly<{}>) {
//     super(props);

//   }

//   //   componentDidMount() {
//   //     const caller = axios.create({
//   //        baseURL: "https://localhost:7017/",
//   //      timeout: 1000,
//   //    });

//   //     caller
//   //       .get("/persons/names")
//   //       .then((response) => {
//   //         console.log(response);
//   //         this.setState({ users: response.data });
//   //       });
//   //   }

//   setNames = (newNames: string[]) => {
// console.log(newNames);
//   };

//   let names: string[""];

//   readNames() {
//     const caller = axios.create({
//       baseURL: "https://localhost:7017/",
//       timeout: 1000,
//     });

//     const [names, setNames] = useState([""]);

//     caller.get("/persons/names").then((response) => {
//       console.log(response);
//       this.setState({names: response.data})
//     });
//   }

//   async renderUsers() {

//     return this.names.map((name) => (
//       <div key={name}>
//         {name} {name}
//       </div>
//     ));
//   }

//   render() {
//     return <div>{this.renderUsers()}</div>;
//   }
// }
