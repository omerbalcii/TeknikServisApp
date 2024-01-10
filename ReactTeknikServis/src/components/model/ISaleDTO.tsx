import { IProduct } from "./IProduct";

export interface ISaleDTO {
  id: number;
  price: number;
  note: string;
  product: string;
  product_id: number;
}
