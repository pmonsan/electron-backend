/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AgentComponent } from 'app/entities/agent/agent.component';
import { AgentService } from 'app/entities/agent/agent.service';
import { Agent } from 'app/shared/model/agent.model';

describe('Component Tests', () => {
  describe('Agent Management Component', () => {
    let comp: AgentComponent;
    let fixture: ComponentFixture<AgentComponent>;
    let service: AgentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AgentComponent],
        providers: []
      })
        .overrideTemplate(AgentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Agent(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.agents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
