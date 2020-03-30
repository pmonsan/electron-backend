import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAgentType } from 'app/shared/model/agent-type.model';

type EntityResponseType = HttpResponse<IAgentType>;
type EntityArrayResponseType = HttpResponse<IAgentType[]>;

@Injectable({ providedIn: 'root' })
export class AgentTypeService {
  public resourceUrl = SERVER_API_URL + 'api/agent-types';

  constructor(protected http: HttpClient) {}

  create(agentType: IAgentType): Observable<EntityResponseType> {
    return this.http.post<IAgentType>(this.resourceUrl, agentType, { observe: 'response' });
  }

  update(agentType: IAgentType): Observable<EntityResponseType> {
    return this.http.put<IAgentType>(this.resourceUrl, agentType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAgentType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgentType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
