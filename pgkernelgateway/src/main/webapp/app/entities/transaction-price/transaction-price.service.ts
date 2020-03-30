import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionPrice } from 'app/shared/model/transaction-price.model';

type EntityResponseType = HttpResponse<ITransactionPrice>;
type EntityArrayResponseType = HttpResponse<ITransactionPrice[]>;

@Injectable({ providedIn: 'root' })
export class TransactionPriceService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-prices';

  constructor(protected http: HttpClient) {}

  create(transactionPrice: ITransactionPrice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionPrice);
    return this.http
      .post<ITransactionPrice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transactionPrice: ITransactionPrice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionPrice);
    return this.http
      .put<ITransactionPrice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransactionPrice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionPrice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(transactionPrice: ITransactionPrice): ITransactionPrice {
    const copy: ITransactionPrice = Object.assign({}, transactionPrice, {
      modificationDate:
        transactionPrice.modificationDate != null && transactionPrice.modificationDate.isValid()
          ? transactionPrice.modificationDate.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.modificationDate = res.body.modificationDate != null ? moment(res.body.modificationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transactionPrice: ITransactionPrice) => {
        transactionPrice.modificationDate = transactionPrice.modificationDate != null ? moment(transactionPrice.modificationDate) : null;
      });
    }
    return res;
  }
}
