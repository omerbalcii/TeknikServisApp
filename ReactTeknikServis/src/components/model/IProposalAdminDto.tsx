import { IProduct } from "./IProduct";
import { ISystemUser } from "./ISystemUser";

export interface IProposalAdminDto {
  id: number;
  note: string;
  price: number;
  user_id: number;
  status: boolean;
  product_id: number;
  username: string;
  name: string;
}
