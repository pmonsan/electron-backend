export interface ICurrency {
  id?: number;
  code?: string;
  longLabel?: string;
  shortLabel?: string;
  active?: boolean;
}

export class Currency implements ICurrency {
  constructor(public id?: number, public code?: string, public longLabel?: string, public shortLabel?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
