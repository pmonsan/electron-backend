/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ConnectorTypeDetailComponent } from 'app/entities/connector-type/connector-type-detail.component';
import { ConnectorType } from 'app/shared/model/connector-type.model';

describe('Component Tests', () => {
  describe('ConnectorType Management Detail Component', () => {
    let comp: ConnectorTypeDetailComponent;
    let fixture: ComponentFixture<ConnectorTypeDetailComponent>;
    const route = ({ data: of({ connectorType: new ConnectorType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ConnectorTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ConnectorTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConnectorTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.connectorType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
