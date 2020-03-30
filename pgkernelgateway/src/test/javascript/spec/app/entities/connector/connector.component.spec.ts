/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ConnectorComponent } from 'app/entities/connector/connector.component';
import { ConnectorService } from 'app/entities/connector/connector.service';
import { Connector } from 'app/shared/model/connector.model';

describe('Component Tests', () => {
  describe('Connector Management Component', () => {
    let comp: ConnectorComponent;
    let fixture: ComponentFixture<ConnectorComponent>;
    let service: ConnectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ConnectorComponent],
        providers: []
      })
        .overrideTemplate(ConnectorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConnectorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConnectorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Connector(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.connectors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
