/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ConnectorTypeComponent } from 'app/entities/connector-type/connector-type.component';
import { ConnectorTypeService } from 'app/entities/connector-type/connector-type.service';
import { ConnectorType } from 'app/shared/model/connector-type.model';

describe('Component Tests', () => {
  describe('ConnectorType Management Component', () => {
    let comp: ConnectorTypeComponent;
    let fixture: ComponentFixture<ConnectorTypeComponent>;
    let service: ConnectorTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ConnectorTypeComponent],
        providers: []
      })
        .overrideTemplate(ConnectorTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConnectorTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConnectorTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ConnectorType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.connectorTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
