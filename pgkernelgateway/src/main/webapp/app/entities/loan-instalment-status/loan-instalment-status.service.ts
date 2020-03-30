import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILoanInstalmentStatus } from 'app/shared/model/loan-instalment-status.model';

type EntityResponseType = HttpResponse<ILoanInstalmentStatus>;
type EntityArrayResponseType = HttpResponse<ILoanInstalmentStatus[]>;

@Injectable({ providedIn: 'root' })
export class LoanInstalmentStatusService {
  public resourceUrl = SERVER_API_URL + 'api/loan-instalment-statuses';

  constructor(protected http: HttpClient) {}

  create(loanInstalmentStatus: ILoanInstalmentStatus): Observable<EntityResponseType> {
    return this.http.post<ILoanInstalmentStatus>(this.resourceUrl, loanInstalmentStatus, { observe: 'response' });
  }

  update(loanInstalmentStatus: ILoanInstalmentStatus): Observable<EntityResponseType> {
    return this.http.put<ILoanInstalmentStatus>(this.resourceUrl, loanInstalmentStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILoanInstalmentStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILoanInstalmentStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
