import { RxStompService } from './rx-stomp.service';
import {myRxStompConfig} from "./stomp.cfg";

export function rxStompServiceFactory() {
  const rxStomp = new RxStompService();
  rxStomp.configure(myRxStompConfig);
  rxStomp.activate();
  return rxStomp;
}
