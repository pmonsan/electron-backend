/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AgentTypeComponent } from 'app/entities/agent-type/agent-type.component';
import { AgentTypeService } from 'app/entities/agent-type/agent-type.service';
import { AgentType } from 'app/shared/model/agent-type.model';

describe('Component Tests', () => {
  describe('AgentType Management Component', () => {
    let comp: AgentTypeComponent;
    let fixture: ComponentFixture<AgentTypeComponent>;
    let service: AgentTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AgentTypeComponent],
        providers: []
      })
        .overrideTemplate(AgentTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgentTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgentTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AgentType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.agentTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
