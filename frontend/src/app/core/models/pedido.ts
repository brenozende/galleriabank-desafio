export interface PedidoItem {
  product?: {
    id?: number;
    description?: string;
    value?: number;
  };
}

export interface Pedido {
  id?: number;
  emissionDate?: string;
  description?: string;
  clientId?: number;
  items?: PedidoItem[];
  products?: number[];
  total?: number;
}
